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

import java.io.File;
import java.io.IOException;

import com.kruchten.columbia.kde.ApproximateDensity;


public class KDEDensityReader
{
	ApproximateDensity[] density = null;
	private String basename;

	public KDEDensityReader(String filename) throws IOException
	{
		this(new File(filename));
	}

	public KDEDensityReader(File file) throws IOException
	{
		this(file.getParentFile().listFiles(new KDEFileFilter(file.getName())));
	}

	public KDEDensityReader(File[] files) throws IOException
	{
		density = new ApproximateDensity[files.length];

		KDEMatrixPopulationReader r = null;

		for(int i = 0; i < density.length; i++)
		{
			r = new KDEMatrixPopulationReader(files[i]);

			int numCols = r.getNumberOfPopulations();

			if((numCols != 2) && (numCols != 3))
			{
				throw new FileFormatException("The density file " + files[i].getName() + " must have either 2 or 3 columns.", files[i]);
			}

			if(r.getPopulation(0).length != r.getPopulation(1).length)
			{
				throw new FileFormatException("There must be an equal number of values and densities.", files[i]);
			}

			if(numCols == 3)
			{
				if(r.getPopulation(0).length != r.getPopulation(2).length)
				{
					throw new FileFormatException("There must be an equal number of values and cumulative densities.", files[i]);
				}
			}

			switch(numCols)
			{
				case 2:
					density[i] = new ApproximateDensity(r.getPopulation(0), r.getPopulation(1));

					break;

				case 3:
					density[i] = new ApproximateDensity(r.getPopulation(0), r.getPopulation(1), r.getPopulation(2));

					break;
			}
		}
	}

	public ApproximateDensity[] getDensites()
	{
		return density;
	}

	public ApproximateDensity getDensity(int i)
	{
		return density[i];
	}

	public int getNumberOfDensities()
	{
		return density.length;
	}

	public String getBasename()
	{
		return basename;
	}

	public void setBasename(String basename)
	{
		this.basename = basename;
	}
}
