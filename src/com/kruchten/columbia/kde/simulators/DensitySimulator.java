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
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.GenF2w32;

import com.kruchten.columbia.kde.ApproximateDensity;
import com.kruchten.columbia.kde.Population;
import com.kruchten.columbia.kde.distributions.KernelDistribution;
import com.kruchten.columbia.kde.fileio.KDEDensityReader;
import com.kruchten.columbia.kde.generators.DirectGenerator;
import com.kruchten.columbia.kde.generators.FastGenerator;
import com.kruchten.columbia.kde.state.State;


public class DensitySimulator extends IndicatorCalculator
{
	private KDEDensityReader reader = null;
	protected RandomVariateGen[] synthesizer = null;

	public final static double zeroTolerance = 1e-25;
	
	public DensitySimulator(State state)
	{
		super(state);
		progressIncrement = (100.0 / 7.0);
	}

	private void doInputFile() throws IOException
	{
		setDescription("reading the input files");

		String filename = state.input.getRawfilename();
		if((filename == null) || (filename == "")) //TODO which is it?
		{
			throw new IllegalStateException("You must specify an input filename");
		}

		String name = state.input.getRawfilename();
		reader = new KDEDensityReader(name);

		reader.setBasename(name.substring(0, name.length() - 4));

		String basename = reader.getBasename();
		setOutputDir(basename);
	}

	protected void doSynthesizer(ContinuousDistribution[] density)
	{
		setDescription("synthesizing populations");
		
		synthesizer = new RandomVariateGen[density.length];

		switch(state.synthesizer.getName())
		{
			case deterministic:
				for(int i = 0; i < synthesizer.length; i++)
				{
					if(state.synthesizer.useRepRatio())
					{
						if(density[i] instanceof KernelDistribution)
						{
							synthesizer[i] = new DirectGenerator((KernelDistribution)density[i], state.synthesizer.getRepRatio(), state.synthesizer.interpolate());
						}
						else if(density[i] instanceof ApproximateDensity)
						{
							synthesizer[i] = new DirectGenerator((ApproximateDensity)density[i], state.synthesizer.getRepRatio(), state.synthesizer.interpolate());
						}
						state.synthesizer.setPopSize(((DirectGenerator)synthesizer[i]).getPopulationSize());
					}
					else
					{
						if(density[i] instanceof KernelDistribution)
						{
							synthesizer[i] = new DirectGenerator((KernelDistribution)density[i], state.synthesizer.getPopSize(), state.synthesizer.interpolate());
						}
						else if(density[i] instanceof ApproximateDensity)
						{
							synthesizer[i] = new DirectGenerator((ApproximateDensity)density[i], state.synthesizer.getPopSize(), state.synthesizer.interpolate());
						}
					}
				}
				break;
	
			case indirect:
				for(int i = 0; i < synthesizer.length; i++)
				{
					synthesizer[i] = new RandomVariateGen(new GenF2w32(), density[i]);
				}
				break;
	
			case direct:
				for(int i = 0; i < synthesizer.length; i++) 
				{
					synthesizer[i] = new FastGenerator((KernelDistribution)density[i]);
				}
				break;
		}

		
		synthPops = new Population[synthesizer.length];

		int syntheticPopulationSize = state.synthesizer.getPopSize();

		for(int i = 0; i < synthPops.length; i++)
		{
			log.debug("synthesizing population # " + i, new Throwable());

			if(synthesizer[i] instanceof DirectGenerator)
			{
				syntheticPopulationSize = ((DirectGenerator)synthesizer[i]).getPopulationSize();
			}

			log.debug("  size: " + syntheticPopulationSize);
			log.debug("  synthesizer: " + synthesizer[i].getClass().toString());

			double[] pop = new double[syntheticPopulationSize];

			for(int j = 0; j < syntheticPopulationSize; j++)
			{
				pop[j] = synthesizer[i].nextDouble();		
				
				if( Math.abs(pop[j]) < zeroTolerance )
				{
					pop[j] = 0;
				}
				
				if((density[i] instanceof KernelDistribution) && (!((KernelDistribution)density[i]).getSupport().isInSupport(pop[j])) && (!(synthesizer[i] instanceof DirectGenerator)))
				{
					j--;
				}
			}

			synthPops[i] = new Population(pop);
		}
	}
	
	protected void writeSynthPopFile() throws IOException
	{
		// Write Synthetic Populations to file
		log.debug("writing synthetic populations to file");
		log.debug("  # of synth pops: " + synthPops.length);
		
		setDescription("writing the synthetic populations to file");
		
		BufferedWriter genFileOut = null;
		
		if((synthesizer[0] instanceof DirectGenerator) && (!state.synthesizer.interpolate()))
		{ 
			genFileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-genstats.txt")));
			genFileOut.write("population\\stats\trepresentation ratio\tpopulation size\n");
		}


		for(int i = 0; i < synthesizer.length; i++)
		{
			if(genFileOut != null)
			{
				genFileOut.write(basename + "-synthpop" + (i+1) + ".txt");
				genFileOut.write("\t" + ((DirectGenerator)synthesizer[i]).getRepresentationRatio());
				genFileOut.write("\t" + ((DirectGenerator)synthesizer[i]).getPopulationSize() + "\n");
			}
			
			BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-synthpop" + (i + 1) + ".txt")));

			int syntheticPopulationSize = synthPops[i].size();
			for(int j = 0; j < syntheticPopulationSize; j++)
			{
				fileOut.write(incomeFormat.format(synthPops[i].getMember(j)));
				fileOut.newLine();
			}

			fileOut.close();
		}		

		if(genFileOut != null)
		{ 
			genFileOut.close();
		}
	}
	
	
	public void runAnalysis() throws Exception
	{
		running = true;

		try
		{
			doInputFile();
			progress += progressIncrement;
			
			basename = new File(reader.getBasename()).getName();
			state.store(outputDirectory, basename);
			progress += progressIncrement;

			ContinuousDistribution[] density = new ContinuousDistribution[reader.getNumberOfDensities()];

			for(int i = 0; i < density.length; i++)
			{
				density[i] = reader.getDensity(i);
			}

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
