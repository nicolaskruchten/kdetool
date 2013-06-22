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

package com.kruchten.columbia.kde.metafile;

import java.util.prefs.Preferences;

import com.kruchten.columbia.kde.state.Analysis;

public abstract class ParameterIterator
{
	protected static Preferences prefs = Preferences.userNodeForPackage(new Analysis().getClass());

	public static ParameterIterator[] getIterators()
	{
		ParameterIterator[] iterators = new ParameterIterator[29];
		int i=0;
		
		iterators[i++] = (new EnumeratedParameterIterator("input file(s)"));
		iterators[i++] = (new EnumeratedParameterIterator("kernel name"));
		iterators[i++] = (new EnumeratedParameterIterator("bandwidth name"));
		iterators[i++] = (new EnumeratedParameterIterator("bandwidth constant"));
		iterators[i++] = (new EnumeratedParameterIterator("bandwidth filename"));
		iterators[i++] = (new EnumeratedParameterIterator("bandwidth weights"));
		iterators[i++] = (new EnumeratedParameterIterator("bandwidth weightsfile"));
		iterators[i++] = (new EnumeratedParameterIterator("support name"));
		iterators[i++] = (new GeneratedParameterIterator("support theta"));
		iterators[i++] = (new EnumeratedParameterIterator("support M"));
		iterators[i++] = (new EnumeratedParameterIterator("support percentage"));
		iterators[i++] = (new EnumeratedParameterIterator("support resolution"));
		iterators[i++] = (new EnumeratedParameterIterator("support filename"));
		iterators[i++] = (new EnumeratedParameterIterator("reflector name"));
		iterators[i++] = (new GeneratedParameterIterator("reflector phi"));
		iterators[i++] = (new EnumeratedParameterIterator("synthesizer name"));
		iterators[i++] = (new EnumeratedParameterIterator("synthesizer use rep ratio"));
		iterators[i++] = (new EnumeratedParameterIterator("synthesizer rep ratio"));
		iterators[i++] = (new EnumeratedParameterIterator("synthesizer pop size"));
		iterators[i++] = (new EnumeratedParameterIterator("synthesizer use interpolation"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator FGT(alpha1) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator FGT(alpha2) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator FGT(alpha3) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator Gini(alpha1) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator Gini(alpha2) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator Atkinson(epsilon1) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator Atkinson(epsilon2) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator GE(alpha1) parameter"));
		iterators[i++] = (new EnumeratedParameterIterator("indicator GE(alpha2) parameter"));
		
		return iterators;
	}
	
	protected static boolean valueWillBeIgnored(String name)
	{
		if(name.equals("bandwidth constant") && (!prefs.get("bandwidth name", "").equals("constant")))
		{
			return true;
		}
		else if(name.equals("bandwidth weightsfile") && (!prefs.get("bandwidth weights", "").equals("true")))
		{
			return true;
		}
		else if(name.equals("bandwidth filename") && (!prefs.get("bandwidth name", "").equals("variablefromfile")))
		{
			return true;
		}
		else if(name.equals("support percentage") && (!prefs.get("support M", "").equals("percentage")))
		{
			return true;
		}
		else if(name.equals("support filename") && (!prefs.get("support name", "").equals("file")))
		{
			return true;
		}
		else if(name.equals("support resolution") && (prefs.get("support name", "").equals("file")))
		{
			return true;
		}
		else if(name.equals("reflector phi") && (!prefs.get("reflector name", "").equals("partial")))
		{
			return true;
		}
		else if(name.equals("synthesizer use rep ratio") && (!prefs.get("synthesizer name", "").equals("deterministic")))
		{
			return true;
		}
		else if(name.equals("synthesizer rep ratio") && ((!prefs.get("synthesizer name", "").equals("deterministic")) 
				|| (!prefs.get("synthesizer use rep ratio", "").equals("true"))))
		{
			return true;
		}
		else if(name.equals("synthesizer pop size") && (prefs.get("synthesizer use rep ratio", "").equals("true")))
		{
			return true;
		}
		else if(name.equals("synthesizer use interpolation") && (!prefs.get("synthesizer name", "").equals("deterministic")))
		{
			return true;
		}
		else if(name.startsWith("indicator") && name.endsWith("parameter"))
		{
			return prefs.getBoolean(name.replaceAll("", "parameter") + "active", false);
		}
		else
		{
			return false;
		}
	}
	
	public abstract boolean hasNextValue();
	
	public abstract void setNextValue();
	
	public abstract void reset();
}
