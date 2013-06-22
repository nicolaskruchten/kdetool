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

package com.kruchten.columbia.kde.test;

import umontreal.iro.lecuyer.probdist.ContinuousDistribution;
import umontreal.iro.lecuyer.probdist.Distribution;

import com.kruchten.columbia.kde.bandwidths.ConstantBandwidth;
import com.kruchten.columbia.kde.distributions.GaussianDist;
import com.kruchten.columbia.kde.distributions.KernelDistribution;


public class KDETest
{
	private ContinuousDistribution distribution;
	private static double[] data = {75};

	public KDETest() throws Throwable
	{
		/*
		 * generator = new KernelDensityGen(new GenF2w32(), new
		 * EmpiricalDist(data), new NormalGen(new GenF2w32(), new
		 * NormalDist()));
		 */
		distribution = new KernelDistribution(data, new GaussianDist(), new ConstantBandwidth(5.0));
	}

	public void printOutput()
	{
		// out = new FileWriter(output);
		for(double d = 50; d < 100.1; d++)
		{
			// out.write(d + ", " + distribution.density(d));
			System.out.println(d + ", " + distribution.density(d));
		}
	}

	public Distribution getDistribution()
	{
		return distribution;
	}

	public static void main(String[] args) throws Throwable
	{
		(new KDETest()).printOutput();
	}
}
