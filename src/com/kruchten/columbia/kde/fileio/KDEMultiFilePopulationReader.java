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


package com.kruchten.columbia.kde.fileio;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;


public class KDEMultiFilePopulationReader extends KDEPopulationReader
{

	protected double[][] populations;

	protected File[] inputFiles;

	protected Logger log;

	public KDEMultiFilePopulationReader(String directoryname, String pattern) throws FileFormatException,
			FileNotFoundException
	{
		File directory = (new File(directoryname));
		log = Logger.getLogger(this.getClass());

		inputFiles = directory.listFiles(new KDEFileFilter(pattern));
		try
		{
			Arrays.sort(inputFiles);
		}
		catch (NullPointerException npe)
		{
			throw new FileNotFoundException("No files were found that match the pattern " + pattern);
		}
		filename = directory + pattern;

		Vector<Object> buffer = new Vector<Object>();

		for (int i = 0; i < inputFiles.length; i++)
		{
			buffer.add((new KDESinglePopulationReader(inputFiles[i])).getPopulation(0));
		}

		numberOfPopulations = buffer.size();
		try
		{
			populationSize = ((double[]) buffer.get(0)).length;
		}
		catch (ArrayIndexOutOfBoundsException aioobe)
		{
			throw new FileNotFoundException("No files in directory " + directory + " where found to match the pattern "
					+ pattern);
		}

		populations = new double[numberOfPopulations][populationSize];

		double[] pop;
		for (int i = 0; i < numberOfPopulations; i++)
		{
			pop = (double[]) buffer.get(i);
			for (int j = 0; j < populationSize; j++)
				populations[i][j] = pop[j];
		}

	}


	public double[] getPopulation(int i)
	{
		double[] pop = new double[populationSize];
		for (int j = 0; j < populationSize; j++)
			pop[j] = populations[i][j];

		return pop;
	}

	public double[][] getPopulations()
	{
		return populations;
	}

	public File[] getInputFiles()
	{
		return inputFiles;
	}

	public File getInputFile(int i)
	{
		return inputFiles[i];
	}
}
