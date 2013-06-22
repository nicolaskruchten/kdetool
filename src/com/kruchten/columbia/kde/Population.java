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

package com.kruchten.columbia.kde;

import umontreal.iro.lecuyer.probdist.EmpiricalDist;

import com.kruchten.columbia.kde.state.Indicators.Filter;


public class Population
{
	protected double[] pop;
	protected EmpiricalDist helper;
	protected double average;

	public Population(double[] data)
	{
		pop = new double[data.length];

		for(int i = 0; i < pop.length; i++)
		{
			pop[i] = data[i];
		}

		java.util.Arrays.sort(pop);

		average = Double.NaN;
		helper = new EmpiricalDist(pop);
	}

	public double[] getPopulation()
	{
		double[] pop = new double[this.pop.length];

		for(int i = 0; i < pop.length; i++)
		{
			pop[i] = this.pop[i];
		}

		return pop;
	}

	public final double getMember(int i)
	{
		return pop[i];
	}

	public final int size()
	{
		return pop.length;
	}

	public double[] getQuantileMeans(int J)
	{
		int lower;
		int upper;
		lower = (int)Math.floor((double)pop.length / J);
		upper = (int)Math.ceil((double)pop.length / J);

		int numberOfBigBins = pop.length - (lower * J);

		double[] quantileMeans = new double[J];

		for(int i = 0; i < J; i++)
		{
			if(i < numberOfBigBins)
			{
				// we are in a big bin
				for(int j = i * upper; j < ((i + 1) * upper); j++)
				{
					quantileMeans[i] += pop[j];
				}

				quantileMeans[i] /= upper;
			}
			else
			{
				// we are in a small bin
				for(int j = (numberOfBigBins * upper) + ((i - numberOfBigBins) * lower);
				    j < ((numberOfBigBins * upper) + (((i + 1) - numberOfBigBins) * lower)); j++)
				{
					quantileMeans[i] += pop[j];
				}

				quantileMeans[i] /= lower;
			}
		}

		return quantileMeans;
	}

	public final double average()
	{
		if(!Double.isNaN(average))
		{
			return average;
		}

		average = 0;

		for(int i = 0; i < pop.length; i++)
		{
			average += pop[i];
		}

		average /= pop.length;

		return average;
	}

	public int numPoor(double z)
	{
		int numPoor = 0;

		for(int i = 0; i < size(); i++)
		{
			if(getMember(i) <= z)
			{
				numPoor++;
			}
			else
			{
				break;
			}
		}

		return numPoor;
	}

	public double poorAvg(double z)
	{
		double avgPoorIncome = 0;
		int numPoor = 0;

		for(int i = 0; i < size(); i++)
		{
			if(getMember(i) <= z)
			{
				numPoor++;
				avgPoorIncome += getMember(i);
			}
			else
			{
				break;
			}
		}

		avgPoorIncome /= numPoor;

		return avgPoorIncome;
	}

	public double getInterQuartileRange()
	{
		double Q1 = 0, Q3 = 0;
		int n = pop.length;
		switch( n % 4) {
		case 0:
			System.out.println("case: 0");
			Q1 = ( pop[ n / 4 ] + pop[ (n / 4) - 1 ] ) / 2.0;
			Q3 = ( pop[ (3 * n) / 4 ] + pop[ ((3 * n) / 4) - 1 ] ) / 2.0;
			break;
		case 1:
			System.out.println("case: 1");
			Q1 = pop[ (n - 1) / 4 ];
			Q3 = pop[ (3 * (n - 1) ) / 4 ];
			break;
		case 2:
			System.out.println("case: 2");
			Q1 = pop[ (n - 2) / 4 ];
			Q3 = pop[ n - (( n + 2) / 4) ];
			break;
		case 3:
			System.out.println("case: 3");
			Q1 = ( pop[ (n + 1) / 4 ] + pop[ (n - 3) / 4 ] ) / 2.0;
			Q3 = ( pop[ n - ((n + 1) / 4) ] + pop[ n - ((n + 5) / 4) ] ) / 2.0;
			break;
		}
		
		System.out.println("Q1: " + Q1 );
		System.out.println("Q3: " + Q3 );
		
		return Q3 - Q1;
	}

	public double getStandardDeviation()
	{
		return helper.getSampleStandardDeviation();
	}

	public Population filteredVersion(Filter filter)
	{
		if(filter == Filter.all)
		{
			return this;
		}
		
		int newPopSize = 0;
		for(double d: pop)
		{
			switch(filter)
			{
				case onlypositive:
					if(d>0)
					{
						newPopSize ++;
					}
					break;
				case onlynonnegative:
					if(d>=0)
					{
						newPopSize ++;
					}
					break;
				case exponentiated:
					newPopSize ++;
					break;
			}
		}
		
		double[] newPop = new double[newPopSize];
		int newI = 0;
		
		for(int i=0; i< pop.length; i++)
		{
			switch(filter)
			{
				case onlypositive:
					if(pop[i]>0)
					{
						newPop[newI] = pop[i];
						newI++;
					}
					break;
				case onlynonnegative:
					if(pop[i]>=0)
					{
						newPop[newI] = pop[i];
						newI++;
					}
					break;
				case exponentiated:
					newPop[newI] = Math.exp(pop[i]);
					newI++;
					break;
			}
		}
		
		return new Population(newPop);
	}
}
