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

import umontreal.iro.lecuyer.probdist.NormalDist;

import com.kruchten.columbia.kde.bandwidths.Bandwidth;
import com.kruchten.columbia.kde.bandwidths.ConstantBandwidth;
import com.kruchten.columbia.kde.distributions.KernelDistribution;
import com.kruchten.columbia.kde.state.Support;
import com.kruchten.columbia.kde.supports.*;

import junit.framework.TestCase;

public class KernelDistributionGaussian extends TestCase {

	KernelDistribution dist;
	DensitySupport support;
	double[] data = {2.265089, 2.358371, 2.396662, 2.427757, 2.472946};
	Bandwidth h;
	
	double tolerance = 0.001;
	
	protected void setUp() throws Exception {
		super.setUp();
		h = new ConstantBandwidth(0.0604725);
		dist = new KernelDistribution(data, new NormalDist(0,1), h );
		support = new FullDensitySupport( dist, Support.MType.bandwidth, 3.0, 100, 0.25);
		dist.setSupport(support);
	}
	
	public void testGaussian() {
		double dx = 0.01;
		double sum = 0.0;
		
		for(double x = 0; x < 6; x += dx ) {
			sum += dist.density(x) * dx;
		}
		
		if ( Math.abs(sum - 1.0) > tolerance) {
			throw new RuntimeException("Error is " + (sum - 1.0) );
		}
		
	}

}
