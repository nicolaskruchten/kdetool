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

import com.kruchten.columbia.kde.indicators.Atkinson;
import com.kruchten.columbia.kde.indicators.CV;
import com.kruchten.columbia.kde.indicators.Entropy;
import com.kruchten.columbia.kde.indicators.FGT;
import com.kruchten.columbia.kde.indicators.GE0;
import com.kruchten.columbia.kde.indicators.GE1;
import com.kruchten.columbia.kde.indicators.Gini;
import com.kruchten.columbia.kde.indicators.HCR;
import com.kruchten.columbia.kde.indicators.I;
import com.kruchten.columbia.kde.indicators.Indicator;
import com.kruchten.columbia.kde.indicators.InequalityIndicator;
import com.kruchten.columbia.kde.indicators.PG;
import com.kruchten.columbia.kde.indicators.PovertyIndicator;
import com.kruchten.columbia.kde.indicators.RMD;
import com.kruchten.columbia.kde.indicators.SDLog;
import com.kruchten.columbia.kde.indicators.SEN;
import com.kruchten.columbia.kde.indicators.SPG;
import com.kruchten.columbia.kde.indicators.Thon;
import com.kruchten.columbia.kde.indicators.Watts;

public class Indicators
{
	public enum Filter {all, onlypositive, onlynonnegative, exponentiated}

	private Filter filter;
	private Vector<PovertyIndicator> povertyIndicators;
	private Vector<InequalityIndicator> inequalityIndicators;
	private Vector<Indicator> allIndicators;
	private String regPrefix = "indicator ";

	public Indicators()
	{
		povertyIndicators = new Vector<PovertyIndicator>();
		inequalityIndicators = new Vector<InequalityIndicator>();
		allIndicators = new Vector<Indicator>();
		
		{
			Indicator indicator;
			indicator = new I();
			addIndicator(indicator);
	
			indicator = new SEN();
			addIndicator(indicator);
	
			indicator = new Watts();
			addIndicator(indicator);
			
			indicator = new HCR();
			addIndicator(indicator);
	
			indicator = new PG();
			addIndicator(indicator);
	
			indicator = new SPG();
			addIndicator(indicator);
	
			indicator = new FGT("1");
			addIndicator(indicator);
	
			indicator = new FGT("2");
			addIndicator(indicator);
			
			indicator = new FGT("3");
			addIndicator(indicator);
			
			indicator = new Thon();
			addIndicator(indicator);
	
			indicator = new Gini("none");
			addIndicator(indicator);
	
			indicator = new Gini("1");
			addIndicator(indicator);
	
			indicator = new Gini("2");
			addIndicator(indicator);
	
			indicator = new RMD();
			addIndicator(indicator);
	
			indicator = new CV();
			addIndicator(indicator);
	
			indicator = new SDLog();
			addIndicator(indicator);
	
			indicator = new Atkinson("1");
			addIndicator(indicator);
	
			indicator = new Atkinson("2");
			addIndicator(indicator);
	
			indicator = new GE0();
			addIndicator(indicator);
	
			indicator = new GE1();
			addIndicator(indicator);
	
			indicator = new Entropy("1");
			addIndicator(indicator);
	
			indicator = new Entropy("2");
			addIndicator(indicator);
		}

		//init from prefs
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		
		for(Indicator indicator: allIndicators)
		{
			indicator.setActive(prefs.getBoolean(regPrefix + indicator.getName() + " active", indicator.isActive()));
			if(indicator.getParameterName() != null)
			{
				indicator.setParameter(prefs.getDouble(regPrefix + indicator.getName() + " parameter", indicator.getParameter()));
			}
		}

		setFilter(Filter.valueOf(prefs.get(regPrefix + "filter", Filter.all.toString())));
	}
	public void addIndicator(Indicator i)
	{
		
		if (i instanceof PovertyIndicator)
		{
			povertyIndicators.add((PovertyIndicator) i);
		}
		else if (i instanceof InequalityIndicator)
		{
			inequalityIndicators.add((InequalityIndicator) i);
		}
		else
		{
			throw new IllegalArgumentException("The class only stores PovertyIndicators and InequalityIndicators.");
		}
		
		allIndicators.add(i);
	}

	public PovertyIndicator getPovertyIndicator(int i)
	{
		return povertyIndicators.get(i);
	}

	public PovertyIndicator[] getPovertyIndicators()
	{
		PovertyIndicator[] p = new PovertyIndicator[povertyIndicators.size()];
		for (int i = 0; i < p.length; i++)
			p[i] = povertyIndicators.get(i);
		return p;
	}

	public int getNumberOfPovertyIndicators()
	{
		return povertyIndicators.size();
	}

	public InequalityIndicator getInequalityIndicator(int i)
	{
		return inequalityIndicators.get(i);
	}

	public int getNumberOfInequalityIndicators()
	{
		return inequalityIndicators.size();
	}

	public InequalityIndicator[] getInequalityIndicators()
	{
		InequalityIndicator[] p = new InequalityIndicator[inequalityIndicators.size()];
		for (int i = 0; i < p.length; i++)
			p[i] = inequalityIndicators.get(i);
		return p;
	}
	
	public void storeToPrefs()
	{
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		prefs.put(regPrefix + "filter", filter.toString());
		
		for(Indicator indicator: allIndicators)
		{
			prefs.putBoolean(regPrefix +  indicator.getName() + " active", indicator.isActive());
			if(indicator.getParameterName() != null)
			{
				prefs.putDouble(regPrefix + indicator.getName() + " parameter", indicator.getParameter());
			}
		}
		
	}
	public String getRegPrefix()
	{
		return regPrefix;
	}
	public Filter getFilter()
	{
		return filter;
	}
	public void setFilter(Filter filter)
	{
		this.filter = filter;
	}
}
