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


public class Gini extends InequalityIndicator
{
	private String paramNum;
	private boolean basic;

	public Gini(String paramNum)
	{
		super();
		this.parameter = 1.0;
		this.paramNum = paramNum;
		this.basic = (paramNum == "none");
	}

	public double getValue(Population pop, double z)
	{
		return Gini.getGINI(pop, z, false, parameter);
	}

	public static double getGINI(Population pop, double z, boolean onlyAmongPoor, double alpha)
	{
		double G = 0;

		for(int i = 0; i < pop.size(); i++)
		{
			if((!onlyAmongPoor) || (pop.getMember(i) <= z))
			{
				for(int j = 0; j < pop.size(); j++)
				{
					if((!onlyAmongPoor) || (pop.getMember(j) <= z))
					{
						if(j != i)
						{
							G += Math.pow(Math.abs(pop.getMember(i) - pop.getMember(j)), alpha);
						}
					}
					else
					{
						break;
					}
				}
			}
			else
			{
				break;
			}
		}

		G = Math.pow(G, 1.0 / alpha);

		if(onlyAmongPoor)
		{
			G = G / (2 * pop.numPoor(z) * pop.numPoor(z) * pop.poorAvg(z));
		}
		else
		{
			G = G / (2 * pop.size() * pop.size() * pop.average());
		}

		return G;
	}

	public String getName()
	{
		if(basic)
		{
			return "Gini";
		}

		return "Gini(" + getParameterName() + ")";
	}

	public String getParameterName()
	{
		if(basic)
		{
			return null;
		}

		return "alpha" + paramNum;
	}
}
