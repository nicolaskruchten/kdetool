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

package com.kruchten.columbia.kde.distributions;

import com.kruchten.columbia.kde.Population;
import com.kruchten.columbia.kde.bandwidths.Bandwidth;
import com.kruchten.columbia.kde.bandwidths.SilvermanBandwidth;
import com.kruchten.columbia.kde.reflectors.Reflector;
import com.kruchten.columbia.kde.supports.DensitySupport;

import umontreal.iro.lecuyer.probdist.*;


public class KernelDistribution extends ContinuousDistribution
{
	protected Population population;
	protected ContinuousDistribution kernel;
	protected Bandwidth bandwidth;
	protected DensitySupport support;
	protected Reflector reflector;

	public KernelDistribution(double[] data, ContinuousDistribution kernel, Bandwidth bandwidth)
	{
		this(data, kernel, bandwidth, null, null);
	}

	public KernelDistribution(double[] population)
	{
		this(population, null, null, null, null);
	}

	public KernelDistribution(double[] data, ContinuousDistribution kernel, Bandwidth bandwidth, DensitySupport support, Reflector reflector)
	{
		super();

		population = new Population(data);
		this.kernel = kernel;
		this.bandwidth = bandwidth;
		this.support = support;
		this.reflector = reflector;
	}

	/**
	 * Computes the density at point x.  Considers the density support.
	 *
	 * @see umontreal.iro.lecuyer.probdist.ContinuousDistribution#density(double)
	 */
	public double density(double x) throws IllegalStateException
	{
		if(!hasBandwidth() || !hasKernel() || !hasPopulation() || !hasDensitySupport() )
		{
			throw new IllegalStateException("The population, kernel, bandwidth and density support must be set before calculating a density.");
		}

		double density = 0;

		if( ! support.isInSupport(x) )
		{
			return 0.0;
		}
		
		for(int i = 0; i < population.size(); i++)
		{
			density += (kernel.density((x - population.getMember(i)) / bandwidth.getBandwidth(i)) / bandwidth.getBandwidth(i));
		}

		try
		{
			// this will throw a NullPointerException if there is no reflector
			double[] reflected = reflector.getReflectedPoints();

			// HARDCODE
			// since reflection is on, we will return a density of zero for negative incomes.
			if( x < 0 )
			{
				return 0.0;
			}
			
			// we assume here that the points in the reflected population are in
			// the same order of those in
			// the original population
			// this means that bandwidth.getBandwidth(i) gives the bandwidth for
			// the ith member of both populations
			for(int i = 0; i < reflected.length; i++)
			{
				density += (kernel.density((x - reflected[i]) / bandwidth.getBandwidth(i)) / bandwidth.getBandwidth(i));
			}
		}
		catch(NullPointerException npe)
		{ // no reflection, no worries
		}

		density /= population.size();

		return density;
	}

	/**
	 * Computes the density at point x.  Does not consider the density support.  
	 *
	 */
	public double rawDensity(double x) throws IllegalStateException
	{
		if(!hasBandwidth() || !hasKernel() || !hasPopulation() )
		{
			throw new IllegalStateException("The population, kernel and bandwidth  must be set before calculating a density.");
		}

		double density = 0;
		
		for(int i = 0; i < population.size(); i++)
		{
			density += (kernel.density((x - population.getMember(i)) / bandwidth.getBandwidth(i)) / bandwidth.getBandwidth(i));
		}

		try
		{
			// this will throw a NullPointerException if there is no reflector
			double[] reflected = reflector.getReflectedPoints();

			// we assume here that the points in the reflected population are in
			// the same order of those in
			// the original population
			// this means that bandwidth.getBandwidth(i) gives the bandwidth for
			// the ith member of both populations
			for(int i = 0; i < reflected.length; i++)
			{
				density += (kernel.density((x - reflected[i]) / bandwidth.getBandwidth(i)) / bandwidth.getBandwidth(i));
			}
			
			density /= (population.size() + reflected.length );
		}
		catch(NullPointerException npe)
		{ // no reflection, no worries
			density /= population.size();
		}
		
		return density;	
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see umontreal.iro.lecuyer.probdist.Distribution#cdf(double)
	 */
	public double cdf(double x) throws IllegalStateException
	{
		if(!hasBandwidth() || !hasKernel() || !hasPopulation() || !hasDensitySupport() )
		{
			throw new IllegalStateException("The population, kernel, bandwidth and density support must be set before calculating the cdf.");
		}
		
		double F = 0.0;
		
		if( x < support.getMin() )
		{
			return 0.0;
		}
		
		if( x > support.getMax() )
		{
			return 1.0;
		}
		
		for(int i = 0; i < population.size(); i++)
		{
			F += kernel.cdf((x - population.getMember(i)) / bandwidth.getBandwidth(i));
		}

		F /= population.size();

		try
		{
			double reflected[] = reflector.getReflectedPoints();
			double reflectionCorrection = 0.0;
			
			if( x < 0)
			{
				return 0;
			}
			
			for(int i = 0; i < reflected.length; i++)
			{
				reflectionCorrection += kernel.cdf((x - reflected[i]) / bandwidth.getBandwidth(i));
			}
			
			reflectionCorrection /= reflected.length;
			reflectionCorrection -= (double) reflected.length / (double) population.size();
			
			F += reflectionCorrection;
		}
		catch(NullPointerException npe)
		{
			// ok, no reflection then...
		}
		
		
		return F;
	}

	public double[] getEstimatedDensities()
	{
		double[] d = new double[support.numberOfEstimatedPoints];
		double[] incomes = support.getDiscretizedSupport();

		for(int i = 0; i < d.length; i++)
		{
			d[i] = density(incomes[i]);
		}

		return d;
	}

	public Reflector getReflector()
	{
		return reflector;
	}

	public void setReflector(Reflector r)
	{
		reflector = r;
	}

	public Bandwidth getBandwidth()
	{
		return bandwidth;
	}

	public void setBandwidth(Bandwidth bandwidth)
	{
		this.bandwidth = bandwidth;
	}

	public ContinuousDistribution getKernel()
	{
		return kernel;
	}

	public void setKernel(ContinuousDistribution kernel)
	{
		this.kernel = kernel;

		if(this.bandwidth instanceof SilvermanBandwidth)
		{
			((SilvermanBandwidth)this.bandwidth).setKernel(kernel);
		}
	}

	public DensitySupport getSupport()
	{
		return support;
	}

	public void setSupport(DensitySupport support)
	{
		this.support = support;
	}

	public Population getPopulation()
	{
		return population;
	}

	public void setPopulation(Population population)
	{
		this.population = population;
	}

	public void setPopulation(double[] data)
	{
		this.population = new Population(data);
	}

	public boolean hasPopulation()
	{
		if(population == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean hasKernel()
	{
		if(kernel == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean hasBandwidth()
	{
		if(bandwidth == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean hasReflector()
	{
		if(reflector == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean hasDensitySupport()
	{
		if(support == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
