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

package com.kruchten.columbia.kde.simulators;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.kruchten.columbia.kde.state.State;
import com.kruchten.columbia.kde.ui.ProgressListener;

public abstract class Simulator implements Runnable, Callable<Object>
{

	protected State state;
	protected Logger log;
	protected double progress;
	protected double progressIncrement;
	protected boolean running;
	protected String description;
	protected File outputDirectory = null;
	protected Vector<ProgressListener> listeners;
	protected DecimalFormat incomeFormat = new DecimalFormat("0.00000E00");
	protected DecimalFormat densityFormat = new DecimalFormat("0.00000E00");
	protected DecimalFormat indexFormat = new DecimalFormat("0.000####");
	protected String basename;

	protected Simulator(State state)
	{
		this.state = state;
		log = Logger.getLogger(this.getClass());

		listeners = new Vector<ProgressListener>();

		running = false;
		progress = 0.0;
		progressIncrement = 1.0;
		description = "not running";
	}

	public static Simulator getSimulator(State state)
	{
		Logger l = Logger.getLogger("com.kruchten.columbia.kde.Simulator");
		
		switch(state.analysis.getType())
		{
			case indicators:
				l.debug("initializing new IndicatorCalculator");
				return new IndicatorCalculator(state);
				
			case synthpop:
				l.debug("initializing new DensitySimulator");
				return new DensitySimulator(state);
				
			case kde:
			case kdeonly:
				l.debug("initializing new KDESimulator");
				return new KDESimulator(state);
				
			default:	
				l.debug("BAD STATE");
				throw new IllegalArgumentException("The specified state object does not contain a valid source value.");	
		}
		
	}

	public abstract void runAnalysis() throws Exception;

	public void run()
	{
		try
		{
			runAnalysis();
		}
		catch (Exception e)
		{
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			else
				throw new RuntimeWrapperException(e);
		}
	}

	public Object call() throws Exception
	{
		runAnalysis();
		return null;
	}

	public boolean isRunning()
	{
		return running;
	}

	public int getProgress()
	{
		return (int) Math.round(progress);
	}

	public String getDescription()
	{
		synchronized (description)
		{
			return description;
		}
	}

	protected void setDescription(String d)
	{
		synchronized (description)
		{
			description = d;
		}

		Enumeration<ProgressListener> e = listeners.elements();

		while (e.hasMoreElements())
		{
			e.nextElement().progressOccured(description);
		}
	}

	public void addProgressListener(ProgressListener pl)
	{
		listeners.add(pl);
	}

	protected void setOutputDir(String basename)
	{
		outputDirectory = new File(basename + "-output1");
		int i = 0;
		
		do
		{
			i++;
			outputDirectory = new File(basename + "-output-" + i);
		} while (outputDirectory.exists());
		
		outputDirectory.mkdir();
		log.debug("outputDirectory = " + outputDirectory);
	}
	

	
}
