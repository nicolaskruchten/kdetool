/*
 
 Please refer to the README file you should have received
 with this source code for further licensing info.
 
Copyright (c) 2005-2006, Kruchten Engineering Services Ltd
(with contributions by David de Koning and Nicolas Kruchten)
All rights reserved.

Redistribution and use in source and binary forms, 
with or without modification, are permitted provided 
that the following conditions are met:

    * Redistributions of source code must retain the 
    above copyright notice, this list of conditions 
    and the following disclaimer.
    * Redistributions in binary form must reproduce 
    the above copyright notice, this list of conditions 
    and the following disclaimer in the documentation 
    and/or other materials provided with the 
    distribution.
    * Neither the name of the <ORGANIZATION> nor the 
    names of its contributors may be used to endorse or
    promote products derived from this software without
    specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED 
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A 
PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY 
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF 
USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.kruchten.columbia.kde.simulators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import umontreal.iro.lecuyer.probdist.ContinuousDistribution;
import umontreal.iro.lecuyer.probdist.UniformDist;

import com.kruchten.columbia.kde.Population;
import com.kruchten.columbia.kde.bandwidths.ConstantBandwidth;
import com.kruchten.columbia.kde.bandwidths.ListedBandwidth;
import com.kruchten.columbia.kde.bandwidths.Silverman1Bandwidth;
import com.kruchten.columbia.kde.bandwidths.Silverman2Bandwidth;
import com.kruchten.columbia.kde.bandwidths.Silverman3Bandwidth;
import com.kruchten.columbia.kde.distributions.EpanechnikovDist;
import com.kruchten.columbia.kde.distributions.GaussianDist;
import com.kruchten.columbia.kde.distributions.KernelDistribution;
import com.kruchten.columbia.kde.distributions.QuarticDist;
import com.kruchten.columbia.kde.distributions.TriangularDist;
import com.kruchten.columbia.kde.distributions.TriweightDist;
import com.kruchten.columbia.kde.fileio.FileFormatException;
import com.kruchten.columbia.kde.fileio.KDEPopulationReader;
import com.kruchten.columbia.kde.reflectors.PartialReflector;
import com.kruchten.columbia.kde.reflectors.TotalReflector;
import com.kruchten.columbia.kde.state.Analysis;
import com.kruchten.columbia.kde.state.Bandwidth;
import com.kruchten.columbia.kde.state.Input;
import com.kruchten.columbia.kde.state.State;
import com.kruchten.columbia.kde.state.Support;
import com.kruchten.columbia.kde.supports.FileFullDensitySupport;
import com.kruchten.columbia.kde.supports.FullDensitySupport;
import com.kruchten.columbia.kde.supports.PositiveDensitySupport;


public class KDESimulator extends DensitySimulator
{
	private KDEPopulationReader reader = null;
	private KDEPopulationReader bandwidthReader = null;
	private KDEPopulationReader fileSupportReader = null;
	private KernelDistribution[] density = null;
	private KDEPopulationReader weightsreader = null;
	
	public KDESimulator(State state)
	{
		super(state);
		if(state.analysis.getType() == Analysis.Type.kde)
		{
			progressIncrement = (100.0 / 16.0);
		}
		else
		{
			progressIncrement = (100.0 / 11.0);
		}
	}
	
	private void doInputFile() throws IllegalStateException, FileFormatException, IOException
	{
		setDescription("reading input files");


		Input.FileType filetype = state.input.getFiletype();
		String filename = state.input.getRawfilename();
		
		if((filename == null) || (filename == "")) //TODO which is it?
		{
			throw new IllegalStateException("You must specify an input filename");
		}
		
		if(state.bandwidth.useWeightsfile() && state.bandwidth.getWeightsfile().equals("")) //TODO is this valid? 
		{
			throw new IllegalStateException("You must specify a bandwidth weights filename");
		}

		if(state.bandwidth.getName() == Bandwidth.Name.variablefromfile)
		{
			if((state.bandwidth.getFilename()== null) || state.bandwidth.getFilename().equals("")) //TODO is this valid?
			{
				throw new IllegalStateException("You must specify a bandwidth filename");
			}
		}

		if(state.support.getName() == Support.Name.file)
		{
			if((state.support.getFilename()== null) || state.support.getFilename().equals("")) //TODO is this valid?
			{
				throw new IllegalStateException("You must specify a support filename");
			}
		}
		

		reader = KDEPopulationReader.readerFactory(filename, filetype);
		setOutputDir(reader.getBasename());
		if(reader.getNumberOfPopulations() == 0)
		{
			throw new FileFormatException("The population input file does not contain any populations.", new File(filename));
		}
		
		density = new KernelDistribution[reader.getNumberOfPopulations()];
		for(int i = 0; i < reader.getNumberOfPopulations(); i++)
		{
			density[i] = new KernelDistribution(reader.getPopulation(i));
		}
		
		if(state.bandwidth.useWeightsfile())
		{
			weightsreader = KDEPopulationReader.readerFactory(state.bandwidth.getWeightsfile(), filetype);
			if((weightsreader.getNumberOfPopulations() != 1) && (weightsreader.getNumberOfPopulations() != density.length))
			{
				throw new FileFormatException("The weights file must contain exactly 1 set of weights or 1 per population.", new File(filename));
			}
		}
		
		if(state.bandwidth.getName() == Bandwidth.Name.variablefromfile)
		{
			bandwidthReader = KDEPopulationReader.readerFactory(state.bandwidth.getFilename(), filetype);
			
			if((bandwidthReader.getNumberOfPopulations() != 1) && (bandwidthReader.getNumberOfPopulations() != density.length))
			{
				throw new IllegalStateException("The bandwidths file must contain 1 set of bandwidths or 1 per population.");
			}
		}
		if(state.support.getName() == Support.Name.file)
		{
			fileSupportReader = KDEPopulationReader.readerFactory(state.support.getFilename(), Input.FileType.single);
		}
	}
	
	private void doDensityOutput()
	{
		setDescription("setting the number of points of estimation");
		
		int numberOfPointsOfEstimation = state.support.getResolution();
		log.debug("  numberOfPointsOfEstimation: " + numberOfPointsOfEstimation);
		
		for(int i = 0; i < density.length; i++)
		{
			density[i].getSupport().setNumberOfEstimatedPoints(numberOfPointsOfEstimation);
		}
	}
	
	
	private void doBandwidth()
	{
		setDescription("calculating bandwidths");

		for(int i = 0; i < density.length; i++)
		{
			double[] weights = null;
			if(weightsreader != null)
			{
				if(weightsreader.getNumberOfPopulations() == 1)
				{
					weights = weightsreader.getPopulation(0);
				}
				else
				{
					weights = weightsreader.getPopulation(i);
				}
			}
			
			switch(state.bandwidth.getName())
			{
			
			case constant:
				density[i].setBandwidth(new ConstantBandwidth(state.bandwidth.getConstantValue(), weights));
				break;
				
			case silverman1:
				density[i].setBandwidth(new Silverman1Bandwidth(new Population(reader.getPopulation(i)),
						density[i].getKernel(), weights));
				break;
				
			case silverman2:
				density[i].setBandwidth(new Silverman2Bandwidth(new Population(reader.getPopulation(i)),
						density[i].getKernel(), weights));
				break;
				
			case silverman3:
				density[i].setBandwidth(new Silverman3Bandwidth(new Population(reader.getPopulation(i)),
						density[i].getKernel(), weights));
				break;
				
			case variablefromfile:
				if(bandwidthReader.getNumberOfPopulations() == 1)
				{
					density[i].setBandwidth(new ListedBandwidth(bandwidthReader.getPopulation(0), weights));
				}
				else
				{
					density[i].setBandwidth(new ListedBandwidth(bandwidthReader.getPopulation(i), weights));
				}
				break;
			
			}
		}
	}
	
	
	private void doDensitySupport()
	{
		setDescription("setting the density support");
		
		Support.MType M = state.support.getM();
		double theta = state.support.getTheta();
		double supportPercentage = state.support.getPercentage();
		int resolution = state.support.getResolution();
		
		switch(state.support.getName())
		{
			case positiveandnegative:
				for(int i = 0; i < density.length; i++)
				{
					density[i].setSupport(new FullDensitySupport(density[i], M, theta, resolution, supportPercentage));
				}
				break;
			case positive:
				for(int i = 0; i < density.length; i++)
				{
					density[i].setSupport(new PositiveDensitySupport(density[i], M, theta, resolution, supportPercentage));
				}
				break;
			case file:
				for(int i = 0; i < density.length; i++)
				{
					FileFullDensitySupport fileFullDensitySupport = new FileFullDensitySupport(density[i], M, theta, resolution, supportPercentage);
					fileFullDensitySupport.setSpecifiedPoints(fileSupportReader.getPopulation(0));
					density[i].setSupport(fileFullDensitySupport);
				}
				break;
		}
		
	}
	
	private void doReflection()
	{
		setDescription("calculating the reflected points");
		
		switch(state.reflector.getName())
		{
			case off:
				for(int i = 0; i < density.length; i++)
				{
					density[i].setReflector(null);
				}
				break;
				
			case total:
				for(int i = 0; i < density.length; i++)
				{
					density[i].setReflector(new TotalReflector(density[i]));
				}
				break;
				
			case partial:
				for(int i = 0; i < density.length; i++)
				{
					density[i].setReflector(new PartialReflector(density[i], state.reflector.getPhi()));
				}
				break;
		}
		
	}
	
	private void doKernel()
	{
		setDescription("initializing the kernels");
		
		ContinuousDistribution kernel = null;
		
		switch(state.kernel.getName())
		{
			case gaussian:
				kernel = new GaussianDist();
				break;
			
			case uniform:
				kernel = new UniformDist(-1,1);
				break;
			
			case epanechnikov:
				kernel = new EpanechnikovDist();
				break;
			
			case quartic:
				kernel = new QuarticDist();
				break;

			case triweight:
				kernel = new TriweightDist();
				break;
				
			case triangular:
				kernel = new TriangularDist();
				break;
		}
		
		for(int i = 0; i < density.length; i++)
		{
			density[i].setKernel(kernel);
		}
		
	}
	
	private void writeBandwidthsFile() throws IOException
	{
		if(state.bandwidth.getName() != Bandwidth.Name.variablefromfile)
		{
			BufferedWriter fileOut;
			// write bandwidths to file
			setDescription("writing the bandwidths to file");
			
			fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-bandwidths.txt")));
			
			for(int i = 0; i < density.length; i++)
			{
				fileOut.write(incomeFormat.format(density[i].getBandwidth().getBandwidth(i)));
				fileOut.newLine();
			}
			
			fileOut.close();
			
			log.debug("The bandwidths have been written to file: " + basename + "-bandwidths.txt");
		}
	}
	
	private void writeDensitySupportsFile() throws IOException
	{
		BufferedWriter fileOut;
		// write density supports to file
		setDescription("writing the density supports to file");
		
		fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-support.txt")));
		
		for(int i = 0; i < density.length; i++)
		{
			fileOut.write(incomeFormat.format(density[i].getSupport().getMin()) + ", " + incomeFormat.format(density[i].getSupport().getMax()));
			if(density[i].getSupport().exogenousOutsideSupport())
			{
				fileOut.write(",*");
			}
			fileOut.newLine();
		}
		
		fileOut.close();
		
		log.debug("The density supports have been written to file: " + basename + "-support.txt");
	}
	
	private void writeDensitiesFile() throws IOException
	{
		setDescription("writing the densities to file");
		
		BufferedWriter fileOut;
		double[] incomes;
		
		
		for(int i = 0; i < density.length; i++)
		{
			int numPointsRequested = state.support.getResolution();
			density[i].getSupport().setNumberOfEstimatedPoints(numPointsRequested);
			incomes = density[i].getSupport().getDiscretizedSupport();
			
			
			
			log.debug(incomes.length + " points estimated");
			if(incomes.length != numPointsRequested)
			{
				log.error("number of points of estimation not what was requested! (asked for " + numPointsRequested+ " but got " + incomes.length+ ")");
			}
			
			fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-density" + (i + 1) + ".txt")));
			
			double maxCDF = 0;
			for(int j = 0; j < incomes.length; j++)
			{
				if(j<(incomes.length-1))
				{
					maxCDF += ((incomes[j+1] - incomes[j]) * density[i].density(incomes[j]));
				}
				fileOut.write(incomeFormat.format(incomes[j]) + ", " + densityFormat.format(density[i].density(incomes[j])));
				fileOut.newLine();
			}		

			log.info("KernelDensity.maxCDF for pop " + i + " (" + density[i].getKernel().getClass().getName() + ") is:" + density[i].cdf(density[i].getSupport().getMax()));
			log.info("Numerically integrated PDF for pop " + i + " (" + density[i].getKernel().getClass().getName() + ") is:" + maxCDF);

			
			fileOut.close();
		}
	}
	
	private void writeReflectionsFile() throws IOException {
		setDescription("writing the reflected densities to file");
		
		BufferedWriter fileOut;
		double[] incomes;
		
		
		for(int i = 0; i < density.length; i++)
		{
			if(density[i].getReflector() == null)	 {
				continue;
			}
			// ReflectedPoints
			// this line will throw a null pointer exception if there is no reflector

			int numPointsRequested = state.support.getResolution();
			density[i].getSupport().setNumberOfEstimatedPoints(numPointsRequested);
			incomes = density[i].getSupport().getDiscretizedSupport();
			
			
			if(incomes.length != numPointsRequested)
			{
				log.error("number of points of estimation not what was requested! (asked for " + numPointsRequested+ " but got " + incomes.length+ ")");
			}
			
			
			fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-reflected-density" + (i + 1) + ".txt")));
			
			for(int j = 0; j < incomes.length; j++)
			{
				fileOut.write(incomeFormat.format(incomes[j]) + ", " + densityFormat.format(density[i].rawDensity(incomes[j])));
				fileOut.newLine();
				if(density[i].getReflector().isReflected(incomes[j])) {
					fileOut.write(incomeFormat.format(-incomes[j]) + ", " + densityFormat.format(density[i].rawDensity(-incomes[j])));
					fileOut.newLine();
				}
			}
			
			fileOut.close();
			
		}
	}
	
	public void runAnalysis() throws IllegalStateException, FileFormatException, Exception
	{
		running = true;
		
		try
		{
			doInputFile();
			progress += progressIncrement;
			
			basename = new File(reader.getBasename()).getName();
			
			state.store(outputDirectory, basename);
			progress += progressIncrement;
			
			doKernel();
			progress += progressIncrement;
			
			doReflection();
			progress += progressIncrement;
			
			doBandwidth();
			progress += progressIncrement;
			
			doDensitySupport();
			progress += progressIncrement;
			
			doDensityOutput();
			progress += progressIncrement;
			
			if(state.analysis.getType() == Analysis.Type.kde)
			{		
				
				doSynthesizer(density);
				progress += progressIncrement;
				
				writeSynthPopFile();
				progress += progressIncrement;

				doPopulationFiltering();
				writePovertyIndicatorsFile(false);
				progress += progressIncrement;
				
				writeInequalityIndicatorsFile(false);
				progress += progressIncrement;
				
				writeQuantileMeansFile(false);
				progress += progressIncrement;
			}
			
			writeDensitiesFile();
			progress += progressIncrement;
			
			writeDensitySupportsFile();
			progress += progressIncrement;
			
			writeBandwidthsFile();
			progress += progressIncrement;
			
			writeReflectionsFile();
			progress += progressIncrement;
		}
		catch(IOException ioe)
		{
			throw ioe;
		}
		finally
		{
			running = false;
		}
	}
}
