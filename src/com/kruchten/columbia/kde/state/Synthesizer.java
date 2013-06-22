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

package com.kruchten.columbia.kde.state;

import java.util.prefs.Preferences;


public class Synthesizer
{
	public enum Name {deterministic, indirect, direct}
	
	private Name name;
	private boolean useRepRatio;
	private double repRatio;
	private boolean interpolate;
	private int popSize;
	private String regPrefix = "synthesizer ";
	
	public Synthesizer()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		setInterpolate(prefs.getBoolean(regPrefix + "use interpolation", false));
		setUseRepRatio(prefs.getBoolean(regPrefix + "use rep ratio", false));
		setPopSize(prefs.getInt(regPrefix + "pop size", 500));
		setRepRatio(prefs.getDouble(regPrefix + "rep ratio", 80));
		setName(Name.valueOf(prefs.get(regPrefix + "name", Name.deterministic.toString())));
	}
	
	public boolean interpolate()
	{
		return interpolate;
	}
	public void setInterpolate(boolean interpolate)
	{
		this.interpolate = interpolate;
	}
	public Name getName()
	{
		return name;
	}
	public void setName(Name name)
	{
		this.name = name;
	}
	public int getPopSize()
	{
		return popSize;
	}
	public void setPopSize(int popSize)
	{
		this.popSize = popSize;
	}
	public double getRepRatio()
	{
		return repRatio;
	}
	public void setRepRatio(double repRatio)
	{
		this.repRatio = repRatio;
	}
	public boolean useRepRatio()
	{
		return useRepRatio;
	}
	public void setUseRepRatio(boolean useRepRatio)
	{
		this.useRepRatio = useRepRatio;
	}

	public void storeToPrefs()
	{		
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put(regPrefix + "name", name.toString());
		prefs.putInt(regPrefix + "pop size", popSize);

		if(name == Name.deterministic)
		{
			prefs.putBoolean(regPrefix + "use interpolation", interpolate);
			prefs.putBoolean(regPrefix + "use rep ratio", useRepRatio);
			if(useRepRatio)
			{
				prefs.putDouble(regPrefix + "rep ratio", repRatio);
				prefs.remove(regPrefix + "pop size");
			}
			else
			{
				prefs.remove(regPrefix + "rep ratio");
			}
		}
		else
		{
			prefs.remove(regPrefix + "use interpolation");
			prefs.remove(regPrefix + "use rep ratio");
			prefs.remove(regPrefix + "rep ratio");
		}
	}

	public String getRegPrefix()
	{
		return regPrefix;
	}

}
