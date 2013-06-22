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

import org.apache.log4j.Logger;

import umontreal.iro.lecuyer.probdist.Distribution;
import umontreal.iro.lecuyer.probdist.UniformDist;

import com.kruchten.columbia.kde.Population;
import com.kruchten.columbia.kde.distributions.EpanechnikovDist;
import com.kruchten.columbia.kde.distributions.GaussianDist;
import com.kruchten.columbia.kde.distributions.QuarticDist;
import com.kruchten.columbia.kde.distributions.TriangularDist;
import com.kruchten.columbia.kde.distributions.TriweightDist;
import com.kruchten.columbia.kde.fileio.FileFormatException;


public class Silverman2Bandwidth extends SilvermanBandwidth
{
	protected double beta;

	public Silverman2Bandwidth(Population data, Distribution kernel)
	{
		super();
		setKernel(kernel);
		this.data = data;
		bandwidth = Double.NaN;
		log = Logger.getLogger(this.getClass());
	}

	public Silverman2Bandwidth(Population data, Distribution kernel, double[] weights) throws FileFormatException
	{
		super( weights );
		setKernel(kernel);
		this.data = data;
		bandwidth = Double.NaN;
		log = Logger.getLogger(this.getClass());
	}

	public void setKernel(Distribution kernel)
	{
		try
		{
			if(kernel instanceof GaussianDist)
			{
				beta = 0.7900;
			}
			else if(kernel instanceof UniformDist)
			{
				beta = 1.3747;
			}
			else if(kernel instanceof QuarticDist)
			{
				beta = 2.0719;
			}
			else if(kernel instanceof EpanechnikovDist)
			{
				beta = 1.7489;
			}
			else if(kernel instanceof TriweightDist)
			{
				beta = 2.3527;
			}
			else if(kernel == null)
			{
				beta = 0;
			}
			else if(kernel instanceof TriangularDist)
			{
				beta = 1.9212;
			}
			else
			{
				throw new IllegalArgumentException("The kernel " + kernel + " is not supported at this time.");
			}
		}
		catch(NullPointerException npe)
		{
			beta = 0;
		}

		this.kernel = kernel;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ca.dekoning.columbia.Bandwidth#getBandwidth()
	 */
	public double getBandwidth()
	{
		if(Double.isNaN(bandwidth))
		{
			log.debug("IQR: " + data.getInterQuartileRange());
			bandwidth = beta * Math.abs(data.getInterQuartileRange()) * Math.pow(data.size(), -0.2);
		}

		return bandwidth;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see ca.dekoning.columbia.Bandwidth#getBandwidth(int)
	 */
	public double getRawBandwidth(int i)
	{
		return getBandwidth();
	}

	public final String getName()
	{
		return "Silverman 2";
	}
}
