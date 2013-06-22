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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;


public class State
{

	public Analysis analysis;
	public Bandwidth bandwidth;
	public Input input;
	public Kernel kernel;
	public Reflector reflector;
	public Support support;
	public Synthesizer synthesizer;
	public Indicators indicators;
	public Quantiles quantiles;
	public PovertyLines povertyLines;
	
	public State()
	{
		init();
	}

	public State(String fileName) throws FileNotFoundException, IOException, InvalidPreferencesFormatException
	{
		Preferences.importPreferences(new FileInputStream(fileName));
		init();
	}

	private void init()
	{
		analysis = new Analysis();
		bandwidth = new Bandwidth();
		input = new Input();
		kernel = new Kernel();
		reflector = new Reflector();
		support = new Support();
		synthesizer = new Synthesizer();
		indicators = new Indicators();
		quantiles = new Quantiles();
		povertyLines = new PovertyLines();
	}

	public void store(File dir, String basename) throws FileNotFoundException, Exception
	{
		analysis.storeToPrefs();
		input.storeToPrefs();
		kernel.storeToPrefs();
		bandwidth.storeToPrefs();
		support.storeToPrefs();
		reflector.storeToPrefs();
		synthesizer.storeToPrefs();
		indicators.storeToPrefs();
		quantiles.storeToPrefs();
		povertyLines.storeToPrefs();
		
		FileOutputStream fileOutputStream = new FileOutputStream(new File(dir, basename + "-parameters.xml"));
		Preferences.userNodeForPackage(this.getClass()).exportSubtree(fileOutputStream);
		
		
		//ok, now we need the user-readable version
		BufferedWriter fileOut = new BufferedWriter(new FileWriter(new File(dir, basename + "-parameters.txt")));

		fileOut.write("**KDE Tool parameter output**");
		fileOut.newLine();
		fileOut.write(new Date().toString());
		fileOut.newLine();
		writePrefsToFile(analysis.getRegPrefix(), fileOut);
		writePrefsToFile(input.getRegPrefix(), fileOut);
		
		switch(analysis.getType())
		{
			case kde:
			case kdeonly:
				writePrefsToFile(kernel.getRegPrefix(), fileOut);
				writePrefsToFile(bandwidth.getRegPrefix(), fileOut);
				writePrefsToFile(support.getRegPrefix(), fileOut);
				writePrefsToFile(reflector.getRegPrefix(), fileOut);
				if(analysis.getType() == Analysis.Type.kdeonly)
				{
					break; //TODO does this break out of the switch?
				}
			case synthpop:
				writePrefsToFile(synthesizer.getRegPrefix(), fileOut);
			case indicators:
				writePrefsToFile(indicators.getRegPrefix(), fileOut);
				writePrefsToFile(quantiles.getRegPrefix(), fileOut);
				writePrefsToFile(povertyLines.getRegPrefix(), fileOut);
				break;
		}
		

		fileOut.close();
	}

	private void writePrefsToFile(String prefix, BufferedWriter fileOut) throws Exception
	{
		fileOut.newLine();		
		Preferences prefs = Preferences.userNodeForPackage(getClass());
		
		String[] keys = prefs.keys();
		for(String key:keys)
		{
			if(key.contains(prefix))
			{
				fileOut.write(key + "\t" + prefs.get(key, null));
				fileOut.newLine();
			}
		}
	}

}
