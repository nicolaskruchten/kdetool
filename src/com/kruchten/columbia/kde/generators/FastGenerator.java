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

import com.kruchten.columbia.kde.distributions.KernelDistribution;

import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.GenF2w32;


public class FastGenerator extends RandomVariateGen
{
	protected RandomVariateGen kernelGen;

	public FastGenerator(KernelDistribution d)
	{
		super(new GenF2w32(), d);
		kernelGen = new RandomVariateGen(new GenF2w32(), d.getKernel());
	}

	public double nextDouble()
	{
		int length = ((KernelDistribution)dist).getPopulation().size();

		int index = (int)Math.floor(stream.nextDouble() * length);

		double next = ((KernelDistribution)dist).getPopulation().getMember(index)
		+ (kernelGen.nextDouble() * ((KernelDistribution)dist).getBandwidth().getBandwidth(index));
		
		if( ( (KernelDistribution) dist ).hasReflector() )
		{
			boolean nextIsGood = false;
			
			do
			{
				
				if( next < 0)
				{
					next = ((KernelDistribution)dist).getPopulation().getMember(index)
						+ (kernelGen.nextDouble() * ((KernelDistribution)dist).getBandwidth().getBandwidth(index));
					
					nextIsGood = false;
				}
				else
				{
					nextIsGood = true;
				}
			} while( !nextIsGood ) ;
		}
		
		return next;
	}
}
