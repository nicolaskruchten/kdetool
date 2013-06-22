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

import com.kruchten.columbia.kde.Population;
import com.kruchten.columbia.kde.fileio.KDEPopulationReader;
import com.kruchten.columbia.kde.indicators.InequalityIndicator;
import com.kruchten.columbia.kde.indicators.PovertyIndicator;
import com.kruchten.columbia.kde.state.*;
import com.kruchten.columbia.kde.state.Indicators.Filter;


public class IndicatorCalculator extends Simulator
{
	private KDEPopulationReader reader = null;
	protected Population[] synthPops;

	public IndicatorCalculator(State state)
	{
		super(state);
		progressIncrement = 20;
	}

	private void doInputFile() throws IOException
	{
		setDescription("reading input files");

		Input.FileType filetype = state.input.getFiletype();
		String filename = state.input.getRawfilename();

		if((filename == null) || (filename == "")) //TODO which is it?
		{
			throw new IllegalStateException("You must specify an input filename");
		}


		reader = KDEPopulationReader.readerFactory(filename, filetype);

		synthPops = new Population[reader.getNumberOfPopulations()];

		for(int i = 0; i < reader.getNumberOfPopulations(); i++)
		{
			synthPops[i] = new Population(reader.getPopulation(i));
		}

		String basename = reader.getBasename();
		setOutputDir(basename);
	}

	protected void doPopulationFiltering() throws IOException
	{
		for(int i=0; i<synthPops.length; i++)
		{
			synthPops[i] = synthPops[i].filteredVersion(state.indicators.getFilter());
		}
		
		if(state.indicators.getFilter() != Filter.all)
		{
			for(int i=0; i<synthPops.length; i++)
			{
				BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-filteredpop" + (i + 1) + ".txt")));
				for(int j=0; j<synthPops[i].size(); j++)
				{
					fileOut.write(incomeFormat.format(synthPops[i].getMember(j)));
					fileOut.newLine();
				}
				fileOut.close();
			}

		}
	}
	
	protected void writeInequalityIndicatorsFile(boolean givenPops) throws IOException
	{

		setDescription("writing inequality indicators to file");

		InequalityIndicator[] ind = state.indicators.getInequalityIndicators();
		boolean atLeastOneActive = false;
		for(int i = 0; i < ind.length; i++)
		{
			if(ind[i].isActive())
			{
				atLeastOneActive = true;
			}
		}
		
		if(!atLeastOneActive)
		{
			return;
		}
		
		BufferedWriter fileOut;
		fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-InequalityIndicators.txt")));

		fileOut.write("stats\\population\t");

		for(int i = 1; i <= synthPops.length; i++)
		{
			if(givenPops)
			{
				fileOut.write("population " + i + "\t");
			}
			else
			{
				fileOut.write(basename + "-synthpop" + i + ".txt\t");
			}
		}

		fileOut.newLine();


		for(int i = 0; i < ind.length; i++)
		{
			if(!ind[i].isActive())
			{
				continue;
			}

			fileOut.write(ind[i].getName());

			if(ind[i].getParameterName() != null)
			{
				fileOut.write(" (" + ind[i].getParameter() + ")");
			}

			fileOut.write("\t");

			for(int j = 0; j < synthPops.length; j++)
			{
				log.debug("starting: " + ind[i].getName() + " on pop#" + (j+1));
				fileOut.write(indexFormat.format(ind[i].getValue(synthPops[j], 0)) + "\t");
			}

			fileOut.newLine();
		}

		fileOut.close();
	}
	
	protected void writeQuantileMeansFile(boolean givenPops) throws IOException
	{
		BufferedWriter fileOut;
		setDescription("writing the quantile means to file");

		double[] means;

		int quantile = 0;

		for(int i = 0; i < state.quantiles.getNumberOfQuantiles(); i++)
		{
			quantile = state.quantiles.getQuantile(i);

			boolean shouldOutput = false;
			for(int j = 0; j < synthPops.length; j++)
			{
				if(quantile <= synthPops[j].size())
				{
					shouldOutput = true;
					break;
				}
			}
			
			if(shouldOutput)
			{
				fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-quantile-" + quantile + ".txt")));

				fileOut.write("quantile\\population\t");

				for(int j = 1; j <= synthPops.length; j++)
				{
					if(quantile <= synthPops[j -1].size())
					{
						if(givenPops)
						{
							fileOut.write("population " + j + "\t");
						}
						else
						{
							fileOut.write(basename + "-synthpop" + j + ".txt\t");
						}
					}
				}

				fileOut.newLine();

				for(int k = 0; k < quantile; k++)
				{
					fileOut.write((k + 1) + "\t");

					for(int j = 0; j < synthPops.length; j++)
					{
						if(quantile <= synthPops[j].size())
						{
							means = synthPops[j].getQuantileMeans(quantile);
							fileOut.write(incomeFormat.format(means[k]) + "\t");
						}
					}

					fileOut.newLine();
				}

				fileOut.newLine();
				fileOut.close();
			}
		}
	}
	
	protected void writePovertyIndicatorsFile(boolean givenPops) throws IOException
	{
		BufferedWriter fileOut;

		// Write Poverty Indicators to file
		setDescription("writing the poverty indicators to file");

		PovertyIndicator[] ind = state.indicators.getPovertyIndicators();
		boolean atLeastOneActive = false;
		for(int i = 0; i < ind.length; i++)
		{
			if(ind[i].isActive())
			{
				atLeastOneActive = true;
			}
		}
		
		if(!atLeastOneActive)
		{
			return;
		}

		double z;

		for(int k = 0; k < state.povertyLines.getNumberOfPovertyLines(); k++)
		{
			z = state.povertyLines.getPovertyLine(k);
			fileOut = new BufferedWriter(new FileWriter(new File(outputDirectory, basename + "-PovertyIndicators-" + z + ".txt")));

			fileOut.write("poverty line = " + indexFormat.format(z));
			fileOut.newLine();
			fileOut.write("stats\\population\t");

			for(int i = 1; i <= synthPops.length; i++)
			{
				if(givenPops)
				{
					fileOut.write("population " + i + "\t");
				}
				else
				{
					fileOut.write(basename + "-synthpop" + i + ".txt\t");
				}
			}

			fileOut.newLine();


			for(int i = 0; i < ind.length; i++)
			{
				if(!ind[i].isActive())
				{
					continue;
				}

				fileOut.write(ind[i].getName());

				if(ind[i].getParameterName() != null)
				{
					fileOut.write("(" + ind[i].getParameter() + ")");
				}

				fileOut.write("\t");

				for(int j = 0; j < synthPops.length; j++)
				{
					log.debug("starting: " + ind[i].getName() + " on pop#" + (j+1) + " with poverty line at " + z);
					fileOut.write(indexFormat.format(ind[i].getValue(synthPops[j], z)) + "\t");
				}

				fileOut.newLine();
			}

			fileOut.close();
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
			
			doPopulationFiltering();
			writePovertyIndicatorsFile(true);
			progress += progressIncrement;
			writeInequalityIndicatorsFile(true);
			progress += progressIncrement;
			writeQuantileMeansFile(true);
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
