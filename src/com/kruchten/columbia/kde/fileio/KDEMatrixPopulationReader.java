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


/**
 * @author David de Koning
 * 
 * 
 */
public class KDEMatrixPopulationReader extends KDEPopulationReader
{

	protected double[][] populations;

	public KDEMatrixPopulationReader(String inputFile) throws FileNotFoundException, IOException
	{
		this(new File(inputFile));
	}

	public KDEMatrixPopulationReader(File inputFile) throws IOException
	{
		Scanner s = null;
		BufferedReader in = new BufferedReader(new FileReader(inputFile));
		Vector<Double[]> popBuffer = new Vector<Double[]>();
		Vector<Double> buffer = new Vector<Double>();

		filename = inputFile.getName();

		// Read the data into Vectors
		boolean first = true;

		try
		{
			while (true)
			{
				buffer.clear();
				s = new Scanner(in.readLine());
				try
				{
					while (s.hasNextDouble())
					{
						buffer.add(new Double(s.nextDouble()));
					}
				}
				catch (InputMismatchException ime)
				{
					throw new FileFormatException("The file " + inputFile.getName() + " is not in the correct format.",
							inputFile);
				}

				popBuffer.add(buffer.toArray(new Double[1]));
				if (first)
				{
					first = false;
					numberOfPopulations = buffer.size();
				}
				else
				{
					if (numberOfPopulations != buffer.size())
						throw new FileFormatException("The File " + inputFile.getName()
								+ " does not contain populations that are the same size.", inputFile);
				}
			}

		}
		catch (NullPointerException npe)
		{
			// Nothing to see here... We've reached the end of the input file,
			// that's all...
		}
		finally
		{
			in.close();
			if (s != null)
				s.close();
		}
		if (numberOfPopulations == 0)
			throw new FileFormatException("The file " + inputFile.getName() + " does not contain any populations.",
					inputFile);

		// Move the data from Vectosr to arrays

		populationSize = popBuffer.size();
		populations = new double[numberOfPopulations][populationSize];

		for (int j = 0; j < populationSize; j++)
		{
			Double[] p = popBuffer.get(j);
			for (int i = 0; i < p.length; i++)
			{
				populations[i][j] = p[i].doubleValue();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.dekoning.columbia.KDEPopulationReader#getPopulation(int)
	 */
	public double[] getPopulation(int i)
	{
		double[] pop = new double[populationSize];
		for (int j = 0; j < populationSize; j++)
			pop[j] = populations[i][j];

		return pop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.dekoning.columbia.KDEPopulationReader#getPopulations()
	 */
	public double[][] getPopulations()
	{
		return populations;
	}

}
