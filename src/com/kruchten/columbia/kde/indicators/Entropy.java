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

package com.kruchten.columbia.kde.indicators;

import com.kruchten.columbia.kde.Population;


public class Entropy extends InequalityIndicator
{
	private String paramNum;

	public Entropy(String paramNum)
	{
		super();
		this.parameter = 1.0;
		this.paramNum = paramNum;
		requiresPositivePop = true;
	}

	public double getValue(Population pop, double z)
	{
		// PERF rearrange formulae for higher performance

		if(Math.abs(parameter - 1.0) < 0.0000001)
		{
			double GE1 = 0.0;
			double x;
			double avg = pop.average();

			for(int i = 0; i < pop.size(); i++)
			{
				x = pop.getMember(i);
				GE1 += ((x / avg) * Math.log(Math.abs(x / avg)));
			}

			GE1 /= pop.size();
			return GE1;
		}
		else if(Math.abs(parameter) < 0.0000001)
		{
			double GE0 = 0.0;

			for(int i = 0; i < pop.size(); i++)
			{
				GE0 += Math.log(Math.abs(pop.getMember(i) / pop.average()));
			}

			GE0 /= pop.size();

			return Math.abs(GE0);
		}
		else
		{
			double GE = 0.0;

			double average = pop.average();
			
			for(int i = 0; i < pop.size(); i++)
			{
				GE += Math.pow( pop.getMember(i) / average, parameter);
			}

			GE /= pop.size();
			
			return (GE - 1.0) / ( (parameter * parameter) - parameter);
		}
	}

	public String getParameterName()
	{
		return "alpha" + paramNum;
	}

	public String getName()
	{
		return "GE(" + getParameterName() + ")";
	}
}
