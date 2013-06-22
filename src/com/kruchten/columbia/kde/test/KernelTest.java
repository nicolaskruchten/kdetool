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

import junit.framework.TestCase;
import umontreal.iro.lecuyer.probdist.UniformDist;

import com.kruchten.columbia.kde.distributions.EpanechnikovDist;
import com.kruchten.columbia.kde.distributions.GaussianDist;
import com.kruchten.columbia.kde.distributions.QuarticDist;
import com.kruchten.columbia.kde.distributions.TriweightDist;

public class KernelTest extends TestCase {

	GaussianDist gaussian;
	UniformDist uniform;
	EpanechnikovDist epanechnikov;
	QuarticDist quartic;
	TriweightDist triweight;
	
	double tolerance;
	
	double x[] = {-1.0,-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1,0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	double delta = x[1] - x[0];
	
	double gaussianValues[] = {0.241970725,0.26608525,0.289691553,0.312253933,0.333224603,0.352065327,0.36827014,0.381387815,0.391042694,0.396952547,0.39894228,0.396952547,0.391042694,0.381387815,0.36827014,0.352065327,0.333224603,0.312253933,0.289691553,0.26608525,0.241970725};
	double uniformValues[] = {0,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0.5,0};
	double epanechnikovValues[] = {0,0.1425,0.27,0.3825,0.48,0.5625,0.63,0.6825,0.72,0.7425,0.75,0.7425,0.72,0.6825,0.63,0.5625,0.48,0.3825,0.27,0.1425,0};
	double quarticValues[] = {0,0.03384375,0.1215,0.24384375,0.384,0.52734375,0.6615,0.77634375,0.864,0.91884375,0.9375,0.91884375,0.864,0.77634375,0.6615,0.52734375,0.384,0.24384375,0.1215,0.03384375,0};
	double triweightValues[] = {0,0.007502031,0.05103,0.145087031,0.28672,0.461425781,0.64827,0.824218281,0.96768,1.061264531,1.09375,1.061264531,0.96768,0.824218281,0.64827,0.461425781,0.28672,0.145087031,0.05103,0.007502031,0};
	
	protected void setUp() throws Exception {
		super.setUp();
		
		gaussian = new GaussianDist();
		uniform = new UniformDist(-1,1);
		epanechnikov = new EpanechnikovDist();
		quartic = new QuarticDist();
		triweight = new TriweightDist();

		tolerance = 0.000001;
	}
	
	public void testGaussian()
	{
		double error = 0.0;
		
		for(int i = 0; i < x.length; i++)
		{
			error += delta * ( Math.abs( gaussian.density(x[i]) - gaussianValues[i] ) );
		}
		
		if( error > tolerance)
		{
			throw new RuntimeException("Gaussian kernel values not computed correctly.  Tolerance = " + tolerance);
		}
	}

	public void testUniform()
	{
		double error = 0.0;
		
		for(int i = 0; i < x.length; i++)
		{
			error += delta * ( Math.abs( uniform.density(x[i]) - uniformValues[i] ) );
		}
					
		if( error > tolerance)
		{
			throw new RuntimeException("Uniform kernel values not computed correctly.  Tolerance = " + tolerance);
		}
	}

	public void testEpanechnikov()
	{
		double error = 0.0;
		
		for(int i = 0; i < x.length; i++)
		{
			error += delta * ( Math.abs( epanechnikov.density(x[i]) - epanechnikovValues[i] ) );
		}
		
		if( error > tolerance)
		{
			throw new RuntimeException("Epanechnikov kernel values not computed correctly.  Tolerance = " + tolerance);
		}
	}

	public void testQuartic()
	{
		double error = 0.0;
		
		for(int i = 0; i < x.length; i++)
		{
			error += delta * ( Math.abs( quartic.density(x[i]) - quarticValues[i] ) );
		}
		
		if( error > tolerance)
		{
			throw new RuntimeException("Quartic kernel values not computed correctly.  Tolerance = " + tolerance);
		}
	}

	public void testTriweight()
	{
		double error = 0.0;
		
		for(int i = 0; i < x.length; i++)
		{
			error += delta * ( Math.abs( triweight.density(x[i]) - triweightValues[i] ) );
		}
					
		if( error > tolerance)
		{
			throw new RuntimeException("Triweight kernel values not computed correctly.  Tolerance = " + tolerance);
		}
	}

}
