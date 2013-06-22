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

import java.util.Vector;
import java.util.prefs.Preferences;

public class PovertyLines
{

	private Vector<Double> povertyLinesContainer = new Vector<Double>();
	private String regPrefix = "poverty lines";
	
	public PovertyLines()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		String[] pLineStrings = prefs.get(regPrefix, "").split(",");
		if(pLineStrings[0].equals(""))
		{
			return;
		}
		for(String q: pLineStrings)
		{
			povertyLinesContainer.add(new Double(q)); //TODO handle nfe
		}
	}
	

	public void addPovertyLine(double z)
	{
		povertyLinesContainer.add(new Double(z));
	}

	public double getPovertyLine(int i)
	{
		return povertyLinesContainer.get(i).doubleValue();
	}

	public double[] getPovertyLines()
	{
		double[] d = new double[povertyLinesContainer.size()];
		for (int i = 0; i < d.length; i++)
			d[i] = povertyLinesContainer.get(i).doubleValue();
		return d;
	}

	public int getNumberOfPovertyLines()
	{
		return povertyLinesContainer.size();
	}

	public double removePovertyLine(int index)
	{
		return povertyLinesContainer.remove(index).doubleValue();
	}

	public void storeToPrefs()
	{
		String csvString = "";
		for(Double q: povertyLinesContainer)
		{
			csvString += q.toString() + ",";
		}
		
		if(csvString.length() > 1)
		{
			csvString = csvString.substring(0,csvString.length()-1); //knock out final comma
		}
		
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put(regPrefix, csvString);
	}


	public String getRegPrefix()
	{
		return regPrefix;
	}
	
}
