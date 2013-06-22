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


package com.kruchten.columbia.kde.reflectors;

import com.kruchten.columbia.kde.distributions.KernelDistribution;

public class PartialReflector extends Reflector
{
	protected double theta;

	/*
	 * The array 'population' must be arranged in ascending order
	 */
	public PartialReflector(KernelDistribution density, double theta)
	{
		this.density = density;
		;
		this.theta = theta;
	}

	public double[] getReflectedPoints()
	{
		double[] points = new double[numberOfReflectedPoints()];

		for(int i = 0; i < points.length; i++)
		{
			points[i] = -1 * density.getPopulation().getMember(i);
		}

		return points;
	}

	private int numberOfReflectedPoints()
	{
		int numberOfReflectedPoints;

		for(numberOfReflectedPoints = 0; numberOfReflectedPoints < density.getPopulation().size(); numberOfReflectedPoints++)
		{
			if(density.getPopulation().getMember(numberOfReflectedPoints) > max())
			{
				break;
			}
		}

		return numberOfReflectedPoints;
	}

	private final double max()
	{
		try
		{
			return density.getPopulation().getMember(0) + (density.getBandwidth().getBandwidth(0) * theta);
		}
		catch(NullPointerException npe)
		{
			throw new IllegalStateException("No bandwidth has been set");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.kruchten.columbia.kde.Reflector#getName()
	 */
	public final String getName()
	{
		return "Partial Reflector";
	}

	public double[] getReflectedPoints(double[] population)
	{
		double max = population[0] + (density.getBandwidth().getBandwidth(0) * theta);
		double[] points;
		int numberOfReflectedPoints;

		for(numberOfReflectedPoints = 0; numberOfReflectedPoints < population.length; numberOfReflectedPoints++)
		{
			if(population[numberOfReflectedPoints] > max)
			{
				break;
			}
		}

		points = new double[numberOfReflectedPoints];

		for(int i = 0; i < points.length; i++)
		{
			points[i] = -1 * population[i];
		}

		return points;
	}

	public double getTheta()
	{
		return theta;
	}

	public void setTheta(double theta)
	{
		this.theta = theta;
	}

	public boolean isReflected(double x)
	{
		if((x > 0) && (x < max()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
