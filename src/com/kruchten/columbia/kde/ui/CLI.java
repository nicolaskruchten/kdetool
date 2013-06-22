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

package com.kruchten.columbia.kde.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.kruchten.columbia.kde.fileio.FileFormatException;
import com.kruchten.columbia.kde.simulators.Simulator;
import com.kruchten.columbia.kde.state.State;

public class CLI
{	
	private Logger log;

	public void go(String[] args)
	{
		log = Logger.getLogger(getClass());
		log.info("KDE Tool running in no-GUI mode");
		for(String stateFileName: args)
		{	
			State state = null;
			Simulator sim = null;
			try
			{
				log.info("Loading file " + stateFileName);
				state = new State(stateFileName);
			}
			catch (Exception e)
			{
				System.out.println("Parameter file error: " + e.getMessage());
				continue;
			}
			
			try
			{
				sim = Simulator.getSimulator(state);
			}
			catch (IllegalArgumentException iae)
			{
				log.error("Simulator initilization error: " + iae.getMessage());
				continue;
			}
		
			try
			{
				sim.runAnalysis();
				log.info("Analysis completed successfully.");
			}
			catch (FileFormatException ffe)
			{
				log.error("IllegalStateException caught", ffe);
			}
			catch (IllegalStateException ise)
			{
				log.error("IllegalStateException caught", ise);
			}
			catch (FileNotFoundException fnfe)
			{
				log.error("FileNotFoundException caught", fnfe);
			}
			catch (IOException ioe)
			{
				log.error("IOException Caught", ioe);
			}
			catch (StringIndexOutOfBoundsException sioobe)
			{
				log.error("StringIndexOutOfBoundsException caught", sioobe);
			}
			catch (Throwable t)
			{
				// this really shouldn't happen.
				log.error("Unknown error caught", t);
			}
		}	
	}
}
