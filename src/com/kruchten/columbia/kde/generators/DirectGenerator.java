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


package com.kruchten.columbia.kde.generators;

import java.util.Arrays;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.kruchten.columbia.kde.ApproximateDensity;
import com.kruchten.columbia.kde.distributions.KernelDistribution;

import umontreal.iro.lecuyer.probdist.Distribution;
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.RandomStream;

public class DirectGenerator extends RandomVariateGen
{

	private double[] syntheticPopulation;
	private int counter;
	private double representationRatio;


	public DirectGenerator(KernelDistribution density, int populationSize, boolean interpolate)
	{
		this(density.getSupport().getDiscretizedSupport(), density.getEstimatedDensities(), populationSize, interpolate);
		this.dist = density;
	}

	public DirectGenerator(KernelDistribution density, double representationRatio, boolean interpolate)
	{
		this(density.getSupport().getDiscretizedSupport(), density.getEstimatedDensities(), representationRatio,
				interpolate);
		this.dist = density;
	}

	public DirectGenerator(ApproximateDensity d, int populationSize, boolean interpolate)
	{
		this(d.getIncomes(), d.getDensities(), populationSize, interpolate);
	}

	public DirectGenerator(ApproximateDensity d, double repRatio, boolean interpolate)
	{
		this(d.getIncomes(), d.getDensities(), repRatio, interpolate);
	}

	public DirectGenerator(double[] incomes, double[] densities, int populationSize, boolean interpolate)
	{
		if (incomes.length != densities.length)
			throw new IllegalArgumentException("The 'incomes' and 'densities' arrays must be the same length");

		Logger log = Logger.getLogger(this.getClass());


		Vector<Double> pop = new Vector<Double>();
		int n;
		int representation = 0;
		double delta = (incomes[incomes.length - 1] - incomes[0]) / incomes.length;
		double syntheticIncome;
		try
		{
			for (int i = 0; i < densities.length; i++)
			{
				n = (int) Math.round(densities[i] * delta * populationSize);

				if (n > 0)
				{
					representation++;
				}
				else
				{
					continue;
				}
				
				
				if (!interpolate)
				{
					for (int j = 0; j < n; j++)
					{
						pop.add(incomes[i]);
					}
				}
				else
				{
					double d = delta / n;
					if (i == 0)
					{ // special case: lower bound
						if (n % 2 == 0)
						{ // even
							double lowerbound = Math.max(0, incomes[0] - (delta / 2));
							n /= 2; // n is now the number of incomes below or
							// above incomes[0]

							d = (incomes[0] - lowerbound) / n;
							for (syntheticIncome = lowerbound + d / 2.0; syntheticIncome < incomes[0]; syntheticIncome += d)
							{
								pop.add(syntheticIncome);
							}

							d = delta / (2.0 * n);
							syntheticIncome = incomes[0] + d / 2.0;
							for (int j = 0; j < n; j++)
							{
								pop.add(syntheticIncome);
								syntheticIncome += d;
							}
						}
						else
						{ // odd
							double lowerbound = Math.max(0, incomes[0] - (delta / 2));
							n -= 1;
							n /= 2;

							d = (incomes[0] - lowerbound) / (n - 0.5);
							syntheticIncome = lowerbound + d / 2.0;
							for (int j = 0; j < n; j++)
							{
								pop.add(syntheticIncome);
								syntheticIncome += d;
							}

							pop.add(incomes[0]);

							d = delta / (2.0 * (n + 0.5));
							syntheticIncome = incomes[0] + d;
							for (int j = 0; j < n; j++)
							{
								pop.add(syntheticIncome);
								syntheticIncome += d;
							}
						}
					}
					else
					{
						syntheticIncome = incomes[i] - ((delta - d) / 2.0);
						for (int j = 0; j < n; j++)
						{
							pop.add(syntheticIncome);
							syntheticIncome += d;
						}
					}
				}
			}
		}
		catch (ArrayIndexOutOfBoundsException aioobe)
		{
			// the whole population has been filled. woo hoo
		}


		syntheticPopulation = new double[pop.size()];
		for (int i = 0; i < pop.size(); i++)
		{
			syntheticPopulation[i] = pop.get(i);
		}

		log.debug("  requested populationSize = " + populationSize, new Throwable());
		log.debug("  resulting size: " + syntheticPopulation.length);
		
		if(syntheticPopulation.length == 0)
		{
			throw new IllegalStateException("The Deterministic Generator could generate such a small population for this number of estimates.");
		}
		
		if(Math.abs((syntheticPopulation.length - populationSize)/populationSize) > 0.5)
		{
			log.warn("resulting is more than 50% out of bounds!");
		}
		
		representationRatio = (representation * 100.0) / densities.length;
		log.debug(" representation ratio: " + representationRatio + "%");

		this.counter = 0;
	}

	public DirectGenerator(double[] incomes, double[] densities, double representationRatio, boolean interpolate)
	{
		this(incomes, densities, calculatePopulationSize(incomes, densities, representationRatio), interpolate);
	}

	public static int calculatePopulationSize(double incomes[], double[] densities, double representationRatio)
	{
		if (incomes.length != densities.length)
			throw new IllegalArgumentException("The 'incomes' and 'densities' arrays must be the same length");

		if ((representationRatio < 0) || (representationRatio > 100))
			throw new IllegalArgumentException("The representation ratio must be between 0 and 100");

		int size = 0;

		double[] sortedDensities = new double[densities.length];
		for (int i = 0; i < densities.length; i++)
			sortedDensities[i] = densities[i];

		Arrays.sort(sortedDensities);

		int index = (int) Math.round((1 - (representationRatio / 100.0)) * densities.length);

		double delta = (incomes[incomes.length - 1] - incomes[0]) / densities.length;

		size = (int) Math.round(0.5 / (sortedDensities[index] * delta));

		return size;
	}

	public double nextDouble()
	{
		return syntheticPopulation[this.counter++];
	}

	public double[] getPopulation()
	{
		return syntheticPopulation;
	}

	public int getPopulationSize()
	{
		return syntheticPopulation.length;
	}

	public Distribution getDistribution()
	{
		return null;
	}

	public RandomStream getStream()
	{
		return null;
	}

	public double getRepresentationRatio()
	{
		return representationRatio;
	}
}
