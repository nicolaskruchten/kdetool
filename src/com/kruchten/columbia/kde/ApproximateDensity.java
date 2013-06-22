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

import com.kruchten.columbia.kde.distributions.KernelDistribution;

import umontreal.iro.lecuyer.probdist.ContinuousDistribution;


public class ApproximateDensity extends ContinuousDistribution
{
	protected double[] x;
	protected double[] density;
	protected double[] cdf;

	public ApproximateDensity(KernelDistribution density)
	{
		this.x = density.getSupport().getDiscretizedSupport();
		this.density = density.getEstimatedDensities();
		this.cdf = new double[density.getSupport().getNumberOfEstimatedPoints()];

		for(int i = 0; i < this.cdf.length; i++)
		{
			this.cdf[i] = density.cdf(x[i]);
		}
	}

	public ApproximateDensity(double[] x, double[] density)
	{
		this(x, density, generateCDF(density, false));
	}

	public ApproximateDensity(double[] x, double[] density, double[] cdf)
	{
		if((x.length != density.length) || (x.length != cdf.length))
		{
			throw new IllegalArgumentException("The arrays 'x', 'density' & 'cdf' must be the same length");
		}

		if(x.length < 2)
		{
			throw new IllegalArgumentException("The density must be specified for at least 2 points.");
		}

		this.x = x;
		this.density = density;
		this.cdf = cdf;
	}

	public int getNumberOfEstimatedPoints()
	{
		return x.length;
	}

	public double density(double x)
	{
		if(x < this.x[0])
		{
			return 0;
		}

		// PERF we could use a binary search for this
		for(int i = 1; i < this.x.length; i++)
		{
			if(x <= this.x[i])
			{
				return density[i - 1] + (((density[i] - density[i - 1]) * (x - this.x[i - 1])) / (this.x[i] - this.x[i - 1]));
			}
		}

		// if we have reached this points, it means that x is outside the
		// density support (on the +ve side)
		return 0;
	}

	public double cdf(double x)
	{
		if(x < this.x[0])
		{
			return 0;
		}

		// PERF we could use a binary search here
		for(int i = 1; i < this.x.length; i++)
		{
			if(x <= this.x[i])
			{
				return cdf[i - 1] + (((cdf[i] - cdf[i - 1]) * (x - this.x[i - 1])) / (this.x[i] - this.x[i - 1]));
			}
		}

		// if we have reached this points, it means that x is outside the
		// density support (on the +ve side)
		return 1;
	}

	public double inverseF(double cdf)
	{
		if(cdf < this.cdf[0])
		{
			return x[0];
		}

		// PERF we could use a binary search here
		for(int i = 1; i < this.cdf.length; i++)
		{
			if(cdf <= this.cdf[i])
			{
				return x[i - 1] + (((x[i] - x[i - 1]) * (cdf - this.cdf[i - 1])) / (this.cdf[i] - this.cdf[i - 1]));
			}
		}

		// if we have reached this points, it means that x is outside the
		// density support (on the +ve side)
		return x[x.length - 1];
	}

	public static double[] generateCDF(double[] density, boolean normalizeCDF)
	{
		if(density.length < 2)
		{
			throw new IllegalArgumentException("You must specify the density at at least 2 points.");
		}

		double[] cdf = new double[density.length];
		cdf[0] = density[0];

		for(int i = 1; i < cdf.length; i++)
		{
			cdf[i] = cdf[i - 1] + density[i];
		}

		if(normalizeCDF)
		{
			double max = cdf[cdf.length - 1];

			if((max < 0.8))
			{
				throw new UnsupportedOperationException("The area under the density is less than 0.8.  This deviation is too great to normalize. ");
			}

			if((max > 1.2))
			{
				throw new UnsupportedOperationException("The area under the density is greater than 1.2.  This deviation is too great to normalize. ");
			}

			for(int i = 0; i < cdf.length; i++)
			{
				cdf[i] = cdf[i] / max;
			}
		}

		return cdf;
	}

	public double[] getDensities()
	{
		return density;
	}

	public double[] getIncomes()
	{
		return x;
	}
}
