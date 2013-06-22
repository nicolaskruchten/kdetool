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


package com.kruchten.columbia.kde.bandwidths;

import umontreal.iro.lecuyer.probdist.Distribution;
import umontreal.iro.lecuyer.probdist.UniformDist;

import com.kruchten.columbia.kde.Population;
import com.kruchten.columbia.kde.distributions.EpanechnikovDist;
import com.kruchten.columbia.kde.distributions.GaussianDist;
import com.kruchten.columbia.kde.distributions.QuarticDist;
import com.kruchten.columbia.kde.distributions.TriangularDist;
import com.kruchten.columbia.kde.distributions.TriweightDist;
import com.kruchten.columbia.kde.fileio.FileFormatException;


public class Silverman3Bandwidth extends SilvermanBandwidth
{
	protected double delta;

	public Silverman3Bandwidth(Population data, Distribution kernel)
	{
		super();
		setKernel(kernel);
		this.data = data;
		bandwidth = Double.NaN;
	}
	
	public Silverman3Bandwidth(Population data, Distribution kernel, double[] weights) throws FileFormatException
	{
		super( weights );
		setKernel(kernel);
		this.data = data;
		bandwidth = Double.NaN;
	}

	public void setKernel(Distribution kernel)
	{
		try
		{
			if(kernel instanceof GaussianDist)
			{
				delta = 0.9000;
			}
			else if(kernel instanceof UniformDist)
			{
				delta = 1.5661;
			}
			else if(kernel instanceof QuarticDist)
			{
				delta = 2.3604;
			}
			else if(kernel instanceof EpanechnikovDist)
			{
				delta = 1.9924;
			}
			else if(kernel instanceof TriweightDist)
			{
				delta = 2.6803;
			}
			else if(kernel instanceof TriangularDist)
			{
				delta = 2.1888;
			}
			else if(kernel == null)
			{
				delta = 0;
			}
			else
			{
				throw new IllegalArgumentException("The kernel " + kernel + " is not supported at this time.");
			}
		}
		catch(NullPointerException npe)
		{
			delta = 0;
		}

		this.kernel = kernel;
	}

	public double getBandwidth()
	{
		if(Double.isNaN(bandwidth))
		{
			bandwidth = delta * Math.min(Math.abs(data.getInterQuartileRange()) / 1.34, data.getStandardDeviation()) * Math.pow(data.size(), -0.2);
		}

		return bandwidth;
	}

	public double getRawBandwidth(int i)
	{
		return getBandwidth();
	}

	public final String getName()
	{
		return "Silverman 3";
	}
}
