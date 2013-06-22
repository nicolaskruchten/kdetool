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


public class Support
{
	public enum Name { positiveandnegative, positive, file}
	public enum MType {bandwidth, stddev, percentage}

	private Name name;
	private double percentage;
	private MType M;
	private int resolution;
	private double theta;
	private String filename;
	private String regPrefix = "support ";
	
	public Support()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		setTheta(prefs.getDouble(regPrefix + "theta", 1.0));
		setPercentage(prefs.getDouble(regPrefix + "percentage", 0.0));
		setResolution(prefs.getInt(regPrefix + "resolution", 100));
		setM(MType.valueOf(prefs.get(regPrefix + "M", MType.bandwidth.toString())));
		setName(Name.valueOf(prefs.get(regPrefix + "name", Name.positiveandnegative.toString())));
		setFilename(prefs.get(regPrefix + "filename", ""));
	}
	
	public MType getM()
	{
		return M;
	}
	public void setM(MType m)
	{
		M = m;
	}
	public Name getName()
	{
		return name;
	}
	public void setName(Name name)
	{
		this.name = name;
	}
	public double getPercentage()
	{
		return percentage;
	}
	public void setPercentage(double percentage)
	{
		this.percentage = percentage;
	}
	public int getResolution()
	{
		return resolution;
	}
	public void setResolution(int resolution)
	{
		this.resolution = resolution;
	}
	public double getTheta()
	{
		return theta;
	}
	public void setTheta(double theta)
	{
		this.theta = theta;
	}
	public void storeToPrefs()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put(regPrefix + "name", name.toString());

		prefs.putDouble(regPrefix + "theta", theta);
		
		prefs.put(regPrefix + "M", M.toString());
		if(M == MType.percentage)
		{
			prefs.putDouble(regPrefix + "percentage", percentage);
		}
		else
		{
			prefs.remove(regPrefix + "percentage");
		}
		
		if(name == Name.file)
		{
			prefs.put(regPrefix + "filename", filename);
			prefs.remove(regPrefix + "resolution");
		}
		else
		{
			prefs.putInt(regPrefix + "resolution", resolution);
			prefs.remove(regPrefix + "filename");
		}
	}

	public String getRegPrefix()
	{
		return regPrefix;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String fileName)
	{
		this.filename = fileName;
	}
}
