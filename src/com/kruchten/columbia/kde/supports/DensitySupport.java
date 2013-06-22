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

package com.kruchten.columbia.kde.supports;

import com.kruchten.columbia.kde.distributions.KernelDistribution;
import com.kruchten.columbia.kde.state.Support;

public abstract class DensitySupport
{
	private static final double precision = 0.0000001;
	
	protected double percentage;
	protected Support.MType mType;
	protected double theta;

	public double getTheta()
	{
		return theta;
	}

	public double getPercentage()
	{
		return percentage;
	}

	public Support.MType getMType()
	{
		return mType;
	}

	public int numberOfEstimatedPoints;
	protected KernelDistribution density;

	public DensitySupport(KernelDistribution density, Support.MType mType, double theta, int numberOfEstimatedPoints, double percentage) throws IllegalArgumentException
	{

		this.mType = mType;
		this.percentage = percentage;
		this.density = density;

		if(numberOfEstimatedPoints < 2)
		{
			throw new IllegalArgumentException("defaultN must be greater than 2");
		}

		this.numberOfEstimatedPoints = numberOfEstimatedPoints;

		this.theta = theta;
	}

	public abstract double getMin();

	public abstract double getMax();

	public boolean isInSupport(double x)
	{
		if( (x > getMin() - precision) && (x < getMax() + precision) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public double[] getDiscretizedSupport()
	{
		return getDiscretizedSupport(numberOfEstimatedPoints);
	}

	private double[] getDiscretizedSupport(int n)
	{
		double[] support = new double[n];

		double range = getMax() - getMin();
		double delta = range / (n - 1);

		if(n > 0)
		{
			support[0] = getMin();
		}

		for(int i = 1; i < n; i++)
		{
			support[i] = support[i - 1] + delta;
		}

		return support;
	}

	public int getNumberOfEstimatedPoints()
	{
		return numberOfEstimatedPoints;
	}

	public void setNumberOfEstimatedPoints(int numberOfEstimatedPoints)
	{
		this.numberOfEstimatedPoints = numberOfEstimatedPoints;
	}
	
	public boolean exogenousOutsideSupport()
	{
		return false;
	}
}
