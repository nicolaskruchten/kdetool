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


public class Bandwidth
{
	public enum Name {constant, silverman1, silverman2, silverman3, variablefromfile}
	
	private Name name;
	private double constantValue;
	private String filename;
	private String weightsfile;
	private boolean useWeightsfile;
	private String regPrefix = "bandwidth ";
	
	public Bandwidth()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		setUseWeightsfile(prefs.getBoolean(regPrefix + "weights", false));
		setConstantValue(prefs.getDouble(regPrefix + "constant", 250.0));
		setName(Name.valueOf(prefs.get(regPrefix + "name", Name.constant.toString())));
		setFilename(prefs.get(regPrefix + "filename", ""));
		setWeightsfile(prefs.get(regPrefix + "weightsfile", ""));
	}
	
	public double getConstantValue()
	{
		return constantValue;
	}
	public void setConstantValue(double constantValue)
	{
		this.constantValue = constantValue;
	}
	public String getFilename()
	{
		return filename;
	}
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	public Name getName()
	{
		return name;
	}
	public void setName(Name name)
	{
		this.name = name;
	}
	public String getWeightsfile()
	{
		return weightsfile;
	}
	public void setWeightsfile(String weightsfile)
	{
		this.weightsfile = weightsfile;
	}
	public boolean useWeightsfile()
	{
		return useWeightsfile;
	}
	public void setUseWeightsfile(boolean useWeightsfile)
	{
		this.useWeightsfile = useWeightsfile;
	}
	public void storeToPrefs()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put(regPrefix + "name", name.toString());
		prefs.putBoolean(regPrefix + "weights", useWeightsfile);
		if(useWeightsfile)
		{
			prefs.put(regPrefix + "weightsfile", weightsfile);
		}
		else
		{
			prefs.remove(regPrefix + "weightsfile");
		}
		
		if(name == Name.constant)
		{
			prefs.putDouble(regPrefix + "constant", constantValue);
		}
		else
		{
			prefs.remove(regPrefix + "constant");
		}
		
		if(name == Name.variablefromfile)
		{
			prefs.put(regPrefix + "filename", filename);
		}
		else
		{
			prefs.remove(regPrefix + "filename");
		}
	}

	public String getRegPrefix()
	{
		return regPrefix;
	}
}
