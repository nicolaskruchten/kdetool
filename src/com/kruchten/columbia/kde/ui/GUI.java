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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import com.kruchten.columbia.kde.fileio.FileFormatException;
import com.kruchten.columbia.kde.fileio.KDEPopulationReader;
import com.kruchten.columbia.kde.indicators.InequalityIndicator;
import com.kruchten.columbia.kde.indicators.PovertyIndicator;
import com.kruchten.columbia.kde.simulators.Simulator;
import com.kruchten.columbia.kde.state.Analysis;
import com.kruchten.columbia.kde.state.Bandwidth;
import com.kruchten.columbia.kde.state.Input;
import com.kruchten.columbia.kde.state.Kernel;
import com.kruchten.columbia.kde.state.Reflector;
import com.kruchten.columbia.kde.state.State;
import com.kruchten.columbia.kde.state.Support;
import com.kruchten.columbia.kde.state.Synthesizer;
import com.kruchten.columbia.kde.state.Indicators.Filter;



public class GUI extends JFrame
{
	private JPanel jPanel = null;
	private JPanel densityPanel = null;
	private JPanel populationsPanel = null;
	private JPanel indicatorsPanel = null;
	private JPanel sourcePanel = null;
	private JPanel inputFilePanel = null;
	private JPanel densityBandwidthPanel = null;
	private JPanel densityBandwidthWeightsPanel = null;
	private JPanel densitySupportPanel = null;
	private JPanel densityReflectionPanel = null;
	private JPanel densityKernelPanel = null;
	private JPanel densityNumberEstimatesPanel = null;
	private JPanel populationsSizePanel = null;
	private JPanel populationsGeneratorPanel = null;
	private JTextField jTextField = null;
	private JPanel indicatorsPovertyPanel = null;
	private JPanel indicatorsInequalityPanel = null;
	private JPanel indicatorsQuantilePanel = null;
	private JTextField jTextField1 = null;
	private JButton jButton = null;
	private JList jList = null;
	private JList jList1 = null;
	private JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane2 = null;
	private JComboBox jComboBox = null;
	private JPanel jPanel21 = null;
	private JPanel povertyIndicatorSelectorPanel = null;
	private JRadioButton jRadioButton2 = null;
	private JRadioButton jRadioButton3 = null;
	private JRadioButton jRadioButton7 = null;
	private JLabel jLabel8 = null;
	private JComboBox jComboBox1 = null;
	private JLabel jLabel9 = null;
	private JTextField jTextField10 = null;
	private JButton jButton1 = null;
	private JComboBox jComboBox2 = null;
	private JLabel jLabel10 = null;
	private JComboBox jComboBox3 = null;
	private JLabel jLabel11 = null;
	private JTextField jTextField11 = null;
	private JLabel jLabel12 = null;
	private JButton jButton2 = null;
	private JLabel jLabel13 = null;
	private JComboBox jComboBox4 = null;
	private JLabel jLabel14 = null;
	private JLabel jLabel15 = null;
	private JLabel jLabel16 = null;
	private JTextField jTextField13 = null;
	private JTextField jTextField14 = null;
	private JLabel jLabel17 = null;
	private JComboBox jComboBox5 = null;
	private JLabel jLabel18 = null;
	private JTextField jTextField15 = null;
	private JTextField jTextField24 = null;
	private JButton jButton3 = null;
	private ButtonGroup buttonGroup1;
	private JButton jButton4 = null;
	private JButton jButton5 = null;
	private JButton jButton6 = null;
	private JLabel jLabel30 = null;
	private JTextField jTextField2 = null;
	private JPanel jPanel8 = null;
	private JTextField jTextField4 = null;
	private JComboBox jComboBox6 = null;
	private JPanel jPanel1 = null;
	private JRadioButton jRadioButton4 = null;
	private JRadioButton jRadioButton5 = null;
	private ButtonGroup populationsSizeButtons = null;
	private JTextField jTextField6 = null;
	private JButton jButton9 = null;
	private DefaultListModel jListModel;
	private DefaultListModel jListModel1;
	private JPanel jPanel3 = null;
	private JCheckBox[] povertyCheckBoxs;
	private JTextField[] povertyTextFields;
	private JCheckBox[] inequalityCheckBoxs;
	private JTextField[] inequalityTextFields;
	private Timer timer;
	private JCheckBox interpolationCheckbox = null;
	private JRadioButton jRadioButton6 = null;
	protected FutureTask<Object> task;
	protected ProgressMonitor progressMonitor;
	protected Simulator sim;
	private State state = null;
	private Logger log;
	private GUI gui;
	private JLabel jLabel31;
	private JLabel jLabel32;
	private JComboBox jComboBox7;
	private JTextField jTextField7;
	private JButton jButton10;
	private boolean synching = true;
	private JPanel zeroFilterPanel;
	private JRadioButton jRadioButton8;
	private JRadioButton jRadioButton9;
	private JRadioButton jRadioButton10;
	private ButtonGroup buttonGroup2;
	private JRadioButton jRadioButton11;
	private JButton jButton7;
	private JTextField jTextField3;
	private JLabel jLabel19;
	
	public GUI()
	{
		super();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		gui = this;

		log = Logger.getLogger(this.getClass());
		
		state = new State();
		initialize();
		
		synching = false;
		synchUIWithNewState();
		
		setVisible(true);
	}

	private void initialize()
	{
		this.setContentPane(getJPanel());
		this.setSize(1000, 735);
		this.setTitle("Kernel Density Estimation and Analysis Tool");
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				System.exit(0);

			}
		});

	}

	private void synchUIWithNewState()
	{
		synchUIWithState();
		synchTextFields();
		synchQuantiles();
		synchPovertyLines();
		synchFilenames();
		synchIndicators();
	}
	
	private void synchFilenames()
	{
		jTextField10.setText(state.input.getRawfilename());
		jTextField4.setText(state.bandwidth.getFilename());
		jTextField7.setText(state.bandwidth.getWeightsfile());
	}
	
	private void synchQuantiles()
	{
		int[] quantiles = state.quantiles.getQuantiles();
		for(int quantile: quantiles)
		{
			getJListModel1().addElement(new Integer(quantile).toString());
		}
	}
	
	private void synchPovertyLines()
	{
		double[] pLines = state.povertyLines.getPovertyLines();
		for(double pLine: pLines)
		{
			getJListModel().addElement(new Double(pLine).toString());
		}
	}
	
	private void synchUIWithState()
	{
		if(synching)
		{
			return;
		}
		synching= true;
		
		
		switch(state.analysis.getType())
		{		
			case kdeonly:

				getJRadioButton7().setSelected(true);
				
				jComboBox1.removeAllItems();
				jComboBox1.addItem("Single Population File");
				jComboBox1.addItem("Series of Single Pop. Files");
				jComboBox1.addItem("Matrix Population File");
		
				synchKDEPanels(true);
				synchSynthpopPanels(false);
				synchIndicatorPanels(false);
				
				break;
				
			case kde:

				getJRadioButton2().setSelected(true);
				
				if (getJComboBox().getItemCount() == 2)
				{
					getJComboBox().addItem("Direct random population generator");
				}
				
				jComboBox1.removeAllItems();
				jComboBox1.addItem("Single Population File");
				jComboBox1.addItem("Series of Single Pop. Files");
				jComboBox1.addItem("Matrix Population File");
		
				synchKDEPanels(true);
				synchSynthpopPanels(true);
				synchIndicatorPanels(true);
				synchDeterministicGenerator();
				
				break;
				
			case synthpop:
				getJRadioButton3().setSelected(true);
				
				int selectedIndex = jComboBox1.getSelectedIndex();
				jComboBox1.removeAllItems();
				jComboBox1.addItem("Single Density File");
				jComboBox1.addItem("Series of Single Density Files");
				if (selectedIndex == 2)
				{
					jComboBox1.setSelectedIndex(0);
					state.input.setFiletype(Input.FileType.single);
				}
				
				selectedIndex = getJComboBox().getSelectedIndex();
				
				getJComboBox().removeItem(getJComboBox().getItemAt(2));	//remove one of the generators
				if(selectedIndex == 2)
				{
					jComboBox.setSelectedIndex(0);
					state.synthesizer.setName(Synthesizer.Name.deterministic);
				}


				synchKDEPanels(false);
				synchSynthpopPanels(true);
				synchIndicatorPanels(true);
				synchDeterministicGenerator();
				
				break;
				
			case indicators:	
				getJRadioButton6().setSelected(true);
				
				jComboBox1.removeAllItems();
				jComboBox1.addItem("Single Population File");
				jComboBox1.addItem("Series of Single Pop. Files");
				jComboBox1.addItem("Matrix Population File");

				synchKDEPanels(false);
				synchSynthpopPanels(false);
				synchIndicatorPanels(true);
				
				break;
		}
		

		synchComboBoxes();
		
		synching = false;

	}

	private void synchIndicatorPanels(boolean enabled)
	{
		setEnabledOnPanel(getIndicatorsInequalityPanel(), enabled);
		setEnabledOnPanel(getIndicatorsPovertyPanel(), enabled);
		setEnabledOnPanel(getPovertyIndicatorSelectorPanel(), enabled);
		setEnabledOnPanel(getIndicatorsQuantilePanel(), enabled);
		setEnabledOnPanel(getJPanel21(), enabled);
		setEnabledOnPanel(getZeroFilterPanel(), enabled);
		getJList().setEnabled(enabled);
		getJList1().setEnabled(enabled);
		//TODO disbable button clicks when buttons are not enabled
		
		if(enabled)
		{
			synchIndicators();
		}
	}

	private void synchSynthpopPanels(boolean enabled)
	{
		setEnabledOnPanel(populationsSizePanel, enabled);
		setEnabledOnPanel(populationsGeneratorPanel, enabled);
	}

	private void synchIndicators()
	{
		switch(state.indicators.getFilter())
		{
			case all:
				getJRadioButton8().setSelected(true);
				break;
			case onlypositive:
				getJRadioButton9().setSelected(true);
				break;
			case onlynonnegative:
				getJRadioButton10().setSelected(true);
				break;
			case exponentiated:
				getJRadioButton11().setSelected(true);
				break;
		}
		
		for(int i=0; i< povertyTextFields.length; i++)
		{
			PovertyIndicator povertyIndicator = state.indicators.getPovertyIndicator(i);
			
			if(((state.indicators.getFilter() == Filter.all) || (state.indicators.getFilter() == Filter.onlynonnegative)) && (povertyIndicator.requiresPositivePop()))
			{
				povertyIndicator.setActive(false);
				povertyCheckBoxs[i].setEnabled(false);
			}
			else
			{
				povertyCheckBoxs[i].setEnabled(true);
			}
			
			povertyCheckBoxs[i].setSelected(povertyIndicator.isActive());
			if (povertyIndicator.getParameterName() != null)
			{
				povertyTextFields[i].setEnabled(povertyCheckBoxs[i].isSelected());
				povertyTextFields[i].setText(Double.toString(povertyIndicator.getParameter()));
			}
		}
		
		for(int i=0; i< inequalityTextFields.length; i++)
		{
			InequalityIndicator inequalityIndicator = state.indicators.getInequalityIndicator(i);
			
			if(((state.indicators.getFilter() == Filter.all) || (state.indicators.getFilter() == Filter.onlynonnegative)) && (inequalityIndicator.requiresPositivePop()))
			{
				inequalityIndicator.setActive(false);
				inequalityCheckBoxs[i].setEnabled(false);
			}
			else
			{
				inequalityCheckBoxs[i].setEnabled(true);
			}
			
			inequalityCheckBoxs[i].setSelected(inequalityIndicator.isActive());
			if (inequalityIndicator.getParameterName() != null)
			{
				inequalityTextFields[i].setEnabled(inequalityCheckBoxs[i].isSelected());
				inequalityTextFields[i].setText(Double.toString(state.indicators.getInequalityIndicator(i).getParameter()));
			}
		}
	}

	private void synchComboBoxes()
	{
		jComboBox.setSelectedIndex(state.synthesizer.getName().ordinal());
		jComboBox1.setSelectedIndex(state.input.getFiletype().ordinal());
		jComboBox6.setSelectedIndex(state.support.getM().ordinal());
		jComboBox5.setSelectedIndex(state.reflector.getName().ordinal());
		jComboBox4.setSelectedIndex(state.support.getName().ordinal());
		jComboBox2.setSelectedIndex(state.kernel.getName().ordinal());
		jComboBox3.setSelectedIndex(state.bandwidth.getName().ordinal());
		
		if(state.bandwidth.useWeightsfile())
		{
			jComboBox7.setSelectedIndex(1);
		}
		else
		{
			jComboBox7.setSelectedIndex(0);
		}
	}

	private void synchTextFields()
	{
		jTextField.setText(new Integer(state.synthesizer.getPopSize()).toString());
		jTextField13.setText(new Double(state.support.getPercentage()).toString());
		jTextField14.setText(new Double(state.support.getTheta()).toString());
		jTextField15.setText(new Double(state.reflector.getPhi()).toString());
		jTextField2.setText(new Integer(state.support.getResolution()).toString());
		jTextField6.setText(new Double(state.synthesizer.getRepRatio()).toString());
	}

	private void synchKDEPanels(boolean enabled)
	{

		setEnabledOnPanel(densityBandwidthPanel, enabled);
		setEnabledOnPanel(densityReflectionPanel, enabled);
		setEnabledOnPanel(densitySupportPanel, enabled);
		setEnabledOnPanel(densityKernelPanel, enabled);
		setEnabledOnPanel(densityNumberEstimatesPanel, enabled);
		setEnabledOnPanel(densityBandwidthWeightsPanel, enabled);
		
		if(!enabled)
		{
			return;
		}
		
		boolean toggleState;
		toggleState = (state.bandwidth.getName() == Bandwidth.Name.constant);
		{
			jLabel11.setEnabled(toggleState);
			jTextField11.setEnabled(toggleState);
		}
		
		toggleState = (state.bandwidth.getName() == Bandwidth.Name.variablefromfile);
		{
			jLabel12.setEnabled(toggleState);
			jTextField4.setEnabled(toggleState);
			jButton2.setEnabled(toggleState);
		}
		
		toggleState = (state.support.getM() == Support.MType.percentage);
		{
			jTextField13.setEnabled(toggleState);
			jLabel15.setEnabled(toggleState);
		}
		

		toggleState = (state.reflector.getName() == Reflector.Name.partial);
		{
			jLabel18.setEnabled(toggleState);
			jTextField15.setEnabled(toggleState);
		}

		toggleState = (state.bandwidth.useWeightsfile());
		{
			jLabel32.setEnabled(toggleState);
			jButton10.setEnabled(toggleState);
		}
		
		toggleState = (state.support.getName() == Support.Name.file);
		{
			jTextField2.setEnabled(!toggleState);
			getJButton7().setEnabled(toggleState);
			jLabel19.setEnabled(toggleState);
		}
	}


	private void synchDeterministicGenerator()
	{
		getInterpolationCheckbox().setSelected(state.synthesizer.interpolate());
		
		if(state.synthesizer.getName() == Synthesizer.Name.deterministic)
		{
			getJRadioButton5().setEnabled(true);
			getInterpolationCheckbox().setEnabled(true);

			if(state.synthesizer.useRepRatio())
			{
				getJRadioButton5().setSelected(true);
				getJTextField().setEnabled(false);
				getJTextField6().setEnabled(true);
			}
			else
			{
				getJRadioButton4().setSelected(true);
				getJTextField().setEnabled(true);
				getJTextField6().setEnabled(false);
			}
		}
		else
		{
			getInterpolationCheckbox().setEnabled(false);
			getJTextField().setEnabled(true);
			getJTextField6().setEnabled(false);
			getJRadioButton4().setSelected(true);
			getJRadioButton5().setEnabled(false);
		}
	}
	
	private void setEnabledOnPanel(JPanel thePanel, boolean enabled)
	{
		if(thePanel != null)
		{
			Component[] components = thePanel.getComponents();
			for(Component c: components)
			{
				c.setEnabled(enabled);
			}
		}
	}
	
	

	public String finished(FutureTask task) throws Exception
	{
		Logger log = Logger.getLogger(this.getClass());
		
		if (task.isCancelled())
		{
			return "Analysis was cancelled.";
		}

		try
		{
			try
			{
				task.get();
			}
			catch (ExecutionException ee)
			{
				throw ee.getCause();
			}
		}
		catch (FileFormatException ffe)
		{
			log.error("IllegalStateException caught", ffe);
			throw new Exception("Bad File Format: " + ffe.getMessage());
		}
		catch (IllegalStateException ise)
		{
			log.error("IllegalStateException caught", ise);
			throw new Exception("Error: " + ise.getMessage());
		}
		catch (FileNotFoundException fnfe)
		{
			log.error("FileNotFoundException caught", fnfe);
			throw new Exception("File not found: " + fnfe.getMessage());
		}
		catch (IOException ioe)
		{
			log.error("IOException Caught", ioe);
			throw new Exception("Error: " + ioe.getMessage());
		}
		catch (StringIndexOutOfBoundsException sioobe)
		{
			log.error("StringIndexOutOfBoundsException caught", sioobe);
			throw new Exception("Incorrect filenames:  Please ensure all files have a three letter extension.");
		}
		catch (Throwable t)
		{
			// this really shouldn't happen.
			log.error("Unknown error caught", t);
			throw new Exception(t.toString());
		}
		
		return "Analysis performed successfully.";
	}
	
	protected void showError(String message)
	{
		JOptionPane.showMessageDialog(rootPane, message, "KDE Error", JOptionPane.ERROR_MESSAGE);
	}

	protected boolean allInputsAreValid()
	{
		boolean valid = true;
			
			//TODO check this, is it complete?
		
			// check the density panel
			if (getJRadioButton2().isSelected() || getJRadioButton7().isSelected()) //TODO refer to state
			{
				Integer.parseInt(getJTextField2().getText().trim());
				if (getJComboBox3().getSelectedItem().equals("Constant")) //TODO refer to state
				{
					valid = checkTextField(valid, getJTextField11());
				}
				if (getJComboBox6().getSelectedItem().equals("Percentage")) //TODO refer to state
				{
					valid = checkTextField(valid, getJTextField13());
				}
				valid = checkTextField(valid, getJTextField14());

				if (getJComboBox5().getSelectedItem().equals("Partial")) //TODO refer to state
				{
					valid = checkTextField(valid, getJTextField15());
				}

			}

			// check the indicators
			for (int i = 0; i < povertyCheckBoxs.length; i++)
			{
				if ((povertyCheckBoxs[i].isSelected()) && (povertyTextFields[i] != null))
					{
						valid = checkTextField(valid, povertyTextFields[i]);
					}
			}

			for (int i = 0; i < inequalityCheckBoxs.length; i++)
			{
				if ((inequalityCheckBoxs[i].isSelected()) && (inequalityTextFields[i] != null))
				{
					valid = checkTextField(valid, inequalityTextFields[i]);
				}
			}

		return valid;
	}

	private boolean checkTextField(boolean valid, JTextField textField)
	{
		if(textField.getText().trim().equals(""))
		{
			textField.setText("missing");
		}
		
		try
		{
			Double.parseDouble(textField.getText().trim());
		}
		catch (NumberFormatException nfe)
		{
			log.debug("Inputs not valid", nfe);
			valid = false;
		}
		
		return valid;
	}

	protected void showMessage(String message)
	{
		JOptionPane.showMessageDialog(rootPane, message);
	}

	
	///TODO MARKER GUI contents start here
	
	
	private JCheckBox getInterpolationCheckbox()
	{
		if (interpolationCheckbox == null)
		{
			interpolationCheckbox = new JCheckBox();
			interpolationCheckbox.setText("Use interpolation");
			interpolationCheckbox.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					state.synthesizer.setInterpolate(interpolationCheckbox.isSelected());
				}

			});
		}
		return interpolationCheckbox;
	}


	private JTextField getJTextField()
	{
		if (jTextField == null)
		{
			jTextField = new JTextField();
			jTextField.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					log.debug("CHANGING the synthetic population size");
					String contents = jTextField.getText().trim();
					log.debug("  contents: " + contents);
					try
					{
						int value = Integer.parseInt(contents);
						jTextField.setForeground(Color.BLACK);
						state.synthesizer.setPopSize(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});

		}
		return jTextField;
	}

	private JTextField getJTextField1()
	{
		if (jTextField1 == null)
		{
			jTextField1 = new JTextField();
			jTextField1.setPreferredSize(new java.awt.Dimension(80, 20));
			jTextField1.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					String contents = jTextField1.getText().trim();
					try
					{
						Double.parseDouble(contents);
						jTextField1.setForeground(Color.BLACK);
					}
					catch (NumberFormatException nfe)
					{
						jTextField1.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField1;
	}


	private JButton getJButton()
	{
		if (jButton == null)
		{
			jButton = new JButton();
			jButton.setText("Add poverty line");
			jButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String contents = jTextField1.getText().trim();
					try
					{
						state.povertyLines.addPovertyLine(Double.parseDouble(contents));
						getJListModel().addElement(contents);
						jTextField1.setText("");
					}
					catch (Exception er)
					{
						if((contents != null) && (!contents.trim().equals("")))
						{
							// this really shouldn't happen
							log.error("did not add the value '" + contents + "' to the poverty lines", er);
						}
					}
				}
			});
		}
		return jButton;
	}


	private JList getJList()
	{
		if (jList == null)
		{
			jList = new JList(getJListModel());
		}
		return jList;
	}


	private JComboBox getJComboBox()
	{
		if (jComboBox == null)
		{
			jComboBox = new JComboBox();
			jComboBox.addItem("Deterministic population generator");
			jComboBox.addItem("Indirect random population generator");
			jComboBox.addItem("Direct random population generator");
			jComboBox.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							state.synthesizer.setName(Synthesizer.Name.values()[jComboBox.getSelectedIndex()]);
							synchUIWithState();
						}
					}
				}
			});
		}
		return jComboBox;
	}


	private JRadioButton getJRadioButton2()
	{
		if (jRadioButton2 == null)
		{
			jRadioButton2 = new JRadioButton();
			jRadioButton2
					.setText("Generate density from given input populations; synthesize populations; compute indicators");
			jRadioButton2.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					log.debug(jRadioButton2.getText() + "'s state was changed");
					if (jRadioButton2.isSelected())
					{
						state.analysis.setType(Analysis.Type.kde);	
						synchUIWithState();
					}
				}
			});
		}
		return jRadioButton2;
	}

	private JRadioButton getJRadioButton7()
	{
		if (jRadioButton7 == null)
		{
			jRadioButton7 = new JRadioButton();
			jRadioButton7
					.setText("Generate density from given input populations");
			jRadioButton7.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton7.isSelected())
					{
						state.analysis.setType(Analysis.Type.kdeonly);	
						synchUIWithState();
					}
				}
			});
		}
		return jRadioButton7;
	}

	private JRadioButton getJRadioButton8()
	{
		if (jRadioButton8 == null)
		{
			jRadioButton8 = new JRadioButton();
			jRadioButton8.setText("Use all population members");
			jRadioButton8.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton8.isSelected())
					{
						state.indicators.setFilter(Filter.all);
						synchIndicators();
					}
				}
			});
		}
		return jRadioButton8;
	}

	private JRadioButton getJRadioButton9()
	{
		if (jRadioButton9 == null)
		{
			jRadioButton9 = new JRadioButton();
			jRadioButton9.setText("Exclude non-positive population members");
			jRadioButton9.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton9.isSelected())
					{
						state.indicators.setFilter(Filter.onlypositive);
						synchIndicators();
					}
				}
			});
		}
		return jRadioButton9;
	}

	private JRadioButton getJRadioButton10()
	{
		if (jRadioButton10 == null)
		{
			jRadioButton10 = new JRadioButton();
			jRadioButton10.setText("Exclude negative population members");
			jRadioButton10.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton10.isSelected())
					{
						state.indicators.setFilter(Filter.onlynonnegative);
						synchIndicators();
					}
				}
			});
		}
		return jRadioButton10;
	}

	private JRadioButton getJRadioButton11()
	{
		if (jRadioButton11 == null)
		{
			jRadioButton11 = new JRadioButton();
			jRadioButton11.setText("Exponentiate population members");
			jRadioButton11.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton11.isSelected())
					{
						state.indicators.setFilter(Filter.exponentiated);
						synchIndicators();
					}
				}
			});
		}
		return jRadioButton11;
	}
	
	private JRadioButton getJRadioButton3()
	{
		if (jRadioButton3 == null)
		{
			jRadioButton3 = new JRadioButton();
			jRadioButton3.setText("Synthesize populations from given densities; compute indicators");
			jRadioButton3.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					log.debug(jRadioButton3.getText() + "'s state was changed");
					if (jRadioButton3.isSelected())
					{
						state.analysis.setType(Analysis.Type.synthpop);
						synchUIWithState();

					}
				}
			});
		}
		return jRadioButton3;
	}

	private JComboBox getJComboBox1()
	{
		if (jComboBox1 == null)
		{
			jComboBox1 = new JComboBox();
			jComboBox1.addItem("Single Population File");
			jComboBox1.addItem("Series of Single Pop. Files");
			jComboBox1.addItem("Matrix Population File");
			jComboBox1.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							state.input.setFiletype(Input.FileType.values()[jComboBox1.getSelectedIndex()]);
							synchUIWithState();
						}
					}
				}
			});

		}
		return jComboBox1;
	}

	private JTextField getJTextField10()
	{
		if (jTextField10 == null)
		{
			jTextField10 = new JTextField();
			jTextField10.setText("");
			jTextField10.setEditable(false);
			jTextField10.setPreferredSize(new Dimension(220, 20));
			jTextField10.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					jTextField10.setText(state.input.getRawfilename());
				}
			});
			jTextField10.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					state.input.setRawfilename(jTextField10.getText().trim());
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField10;
	}

	private JButton getJButton1()
	{
		if (jButton1 == null)
		{
			jButton1 = new JButton();
			jButton1.setText("Browse...");
			jButton1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Preferences prefs = Preferences.userNodeForPackage(getClass());
					JFileChooser chooser = new JFileChooser(prefs.get("inputfilename", System.getProperty("user.dir")));
					chooser.setFileFilter(chooser.getAcceptAllFileFilter());
					int returnVal = chooser.showOpenDialog(rootPane);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String inputLine = chooser.getSelectedFile().getAbsolutePath();

						try
						{
							if(state.input.getFiletype() == Input.FileType.fileseries)
							{
								try
								{
									inputLine = inputLine.replaceFirst("\\d+", "*");
								}
								catch(Exception e2)
								{
									; //if it fails, the factory will load it as a single file
								}
							}
							KDEPopulationReader.readerFactory(inputLine, state.input.getFiletype());
							prefs.put("inputfilename", (new File(inputLine)).getParentFile().getAbsolutePath());
							jTextField10.setText(inputLine);
						}
						catch(Exception e2) //assume the factory threw an error
						{
							log.error(e2);
							JOptionPane.showMessageDialog(rootPane, "That file is not a valid input file.",
									"Bad input file", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return jButton1;
	}

	private JTextField getJTextField3()
	{
		if (jTextField3 == null)
		{
			jTextField3 = new JTextField();
			jTextField3.setText("");
			jTextField3.setEditable(false);
			jTextField3.setPreferredSize(new Dimension(150, 20));
			jTextField3.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					jTextField3.setText(state.support.getFilename());
				}
			});
			jTextField3.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					state.support.setFilename(jTextField3.getText().trim());
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField3;
	}

	private JButton getJButton7()
	{
		if (jButton7 == null)
		{
			jButton7 = new JButton();
			jButton7.setText("Browse...");
			jButton7.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Preferences prefs = Preferences.userNodeForPackage(getClass());
					JFileChooser chooser = new JFileChooser(prefs.get("supportfilename", System.getProperty("user.dir")));
					chooser.setFileFilter(chooser.getAcceptAllFileFilter());
					int returnVal = chooser.showOpenDialog(rootPane);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String inputLine = chooser.getSelectedFile().getAbsolutePath();

						try
						{
							KDEPopulationReader.readerFactory(inputLine, Input.FileType.single);
							prefs.put("supportfilename", (new File(inputLine)).getParentFile().getAbsolutePath());
							jTextField3.setText(inputLine);
						}
						catch(Exception e2) //assume the factory threw an error
						{
							log.error(e2);
							JOptionPane.showMessageDialog(rootPane, "That file is not a valid input file.",
									"Bad input file", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return jButton7;
	}

	
	private JComboBox getJComboBox2()
	{
		if (jComboBox2 == null)
		{
			jComboBox2 = new JComboBox();
			jComboBox2.addItem("Gaussian");
			jComboBox2.addItem("Uniform");
			jComboBox2.addItem("Epanechnikov");
			jComboBox2.addItem("Quartic");
			jComboBox2.addItem("Triweight");
			jComboBox2.addItem("Triangular");
			jComboBox2.setPreferredSize(new java.awt.Dimension(150, 22));
			jComboBox2.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							state.kernel.setName(Kernel.Name.values()[jComboBox2.getSelectedIndex()]);
						}
					}
				}
			});
		}
		return jComboBox2;
	}

	private JComboBox getJComboBox3()
	{
		if (jComboBox3 == null)
		{
			jComboBox3 = new JComboBox();
			jComboBox3.addItem("Constant");
			jComboBox3.addItem("Silverman 1");
			jComboBox3.addItem("Silverman 2");
			jComboBox3.addItem("Silverman 3");
			jComboBox3.addItem("Variable (from file)");
			jComboBox3.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							state.bandwidth.setName(Bandwidth.Name.values()[jComboBox3.getSelectedIndex()]);
							synchUIWithState();
						}
					}
				}
			});
		}
		return jComboBox3;
	}
	
	private JComboBox getJComboBox7()
	{
		if (jComboBox7 == null)
		{
			jComboBox7 = new JComboBox();
			jComboBox7.setPreferredSize(new Dimension(100, 20));
			jComboBox7.addItem("None");
			jComboBox7.addItem("From file");
			jComboBox7.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							if (e.getItem().equals("From file"))
							{
								state.bandwidth.setWeightsfile(jTextField7.getText());
								state.bandwidth.setUseWeightsfile(true);
							}
							else
							{
								state.bandwidth.setUseWeightsfile(false);
							}
							synchUIWithState();
						}
					}
				}
			});
		}
		return jComboBox7;
	}

	private JTextField getJTextField11()
	{
		if (jTextField11 == null)
		{
			jTextField11 = new JTextField();
			jTextField11.setEnabled(false);
			jTextField11.setText(new Double(state.bandwidth.getConstantValue()).toString());
			jTextField11.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					log.debug("CHANGING the user-specified bandwidth");
					String contents = jTextField11.getText().trim();
					log.debug("  contents: " + contents);
					try
					{
						double value = Double.parseDouble(contents);
						jTextField11.setForeground(Color.BLACK);
						state.bandwidth.setConstantValue(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField11.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField11;
	}

	private JButton getJButton2()
	{
		if (jButton2 == null)
		{
			jButton2 = new JButton();
			jButton2.setText("Browse...");
			jButton2.setEnabled(false);
			jButton2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (!jButton2.isEnabled())
					{
						return;
					}
					Preferences prefs = Preferences.userNodeForPackage(getClass());
					JFileChooser chooser = new JFileChooser(prefs.get("bandwidthfilename", System.getProperty("user.dir")));

					chooser.setFileFilter(chooser.getAcceptAllFileFilter());
					int returnVal = chooser.showOpenDialog(rootPane);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String inputLine = chooser.getSelectedFile().getAbsolutePath();

						try
						{
							if(state.input.getFiletype() == Input.FileType.fileseries)
							{
								try
								{
									inputLine = inputLine.replaceFirst("\\d+", "*");
								}
								catch(Exception e2)
								{
									; //if it fails, the factory will load it as a single file
								}
							}
							KDEPopulationReader.readerFactory(inputLine, state.input.getFiletype());
							prefs.put("bandwidthfilename", (new File(inputLine)).getParentFile().getAbsolutePath());
							jTextField4.setText(inputLine);
						}
						catch(Exception e2) //assume the factory threw an error
						{
							log.error(e2);
							JOptionPane.showMessageDialog(rootPane, "That file is not a valid input file.",
									"Bad input file", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return jButton2;
	}

	private JButton getJButton10()
	{
		if (jButton10 == null)
		{
			jButton10 = new JButton();
			jButton10.setText("Browse...");
			jButton10.setEnabled(false);
			jButton10.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (!jButton10.isEnabled())
					{
						return;
					}
					Preferences prefs = Preferences.userNodeForPackage(getClass());
					JFileChooser chooser = new JFileChooser(prefs.get("weightsfilename", System.getProperty("user.dir")));

					chooser.setFileFilter(chooser.getAcceptAllFileFilter());
					int returnVal = chooser.showOpenDialog(rootPane);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String inputLine = chooser.getSelectedFile().getAbsolutePath();

						try
						{
							if(state.input.getFiletype() == Input.FileType.fileseries)
							{
								try
								{
									inputLine = inputLine.replaceFirst("\\d+", "*");
								}
								catch(Exception e2)
								{
									; //if it fails, the factory will load it as a single file
								}
							}
							KDEPopulationReader.readerFactory(inputLine, state.input.getFiletype());
							prefs.put("weightsfilename", (new File(inputLine)).getParentFile().getAbsolutePath());
							jTextField7.setText(inputLine);
						}
						catch(Exception e2) //assume the factory threw an error
						{
							log.error(e2);
							JOptionPane.showMessageDialog(rootPane, "That file is not a valid input file.",
									"Bad input file", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		return jButton10;
	}

	private JComboBox getJComboBox4()
	{
		if (jComboBox4 == null)
		{
			jComboBox4 = new JComboBox();
			jComboBox4.addItem("Positive and Negative");
			jComboBox4.addItem("Positive Only");
			jComboBox4.addItem("From File");
			
			jComboBox4.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if(!synching)
					{
						state.support.setName(Support.Name.values()[jComboBox4.getSelectedIndex()]);
						synchUIWithState();
					}
				}
			});

		}
		return jComboBox4;
	}

	private JTextField getJTextField13()
	{
		if (jTextField13 == null)
		{
			jTextField13 = new JTextField();
			jTextField13.setEnabled(false);
			jTextField13.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					String contents = jTextField13.getText().trim();
					try
					{
						double value = Double.parseDouble(contents);
						jTextField13.setForeground(Color.BLACK);
						state.support.setPercentage(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField13.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField13;
	}

	private JTextField getJTextField14()
	{
		if (jTextField14 == null)
		{
			jTextField14 = new JTextField();
			jTextField14.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					String contents = jTextField14.getText().trim();
					try
					{
						double value = Double.parseDouble(contents);
						jTextField14.setForeground(Color.BLACK);
						state.support.setTheta(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField14.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField14;
	}

	private JComboBox getJComboBox5()
	{
		if (jComboBox5 == null)
		{
			jComboBox5 = new JComboBox();
			jComboBox5.addItem("None");
			jComboBox5.addItem("Total");
			jComboBox5.addItem("Partial");
			jComboBox5.setPreferredSize(new java.awt.Dimension(150, 22));
			jComboBox5.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							state.reflector.setName(Reflector.Name.values()[jComboBox5.getSelectedIndex()]);
							synchUIWithState();
						}
					}

				}
			});
		}
		return jComboBox5;
	}

	private JTextField getJTextField15()
	{
		if (jTextField15 == null)
		{
			jTextField15 = new JTextField();
			jTextField15.setEnabled(false);
			jTextField15.setPreferredSize(new java.awt.Dimension(60, 20));
			jTextField15.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					String contents = jTextField15.getText().trim();
					try
					{
						double value = Double.parseDouble(contents);
						jTextField15.setForeground(Color.BLACK);
						state.reflector.setPhi(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField15.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField15;
	}

	private JList getJList1()
	{
		if (jList1 == null)
		{
			jList1 = new JList(getJListModel1());
		}
		return jList1;
	}


	private DefaultListModel getJListModel1()
	{
		if (jListModel1 == null)
		{
			jListModel1 = new DefaultListModel();
		}
		return jListModel1;
	}

	private DefaultListModel getJListModel()
	{
		if (jListModel1 == null)
		{
			jListModel = new DefaultListModel();
		}
		return jListModel;
	}

	private JTextField getJTextField24()
	{
		if (jTextField24 == null)
		{
			jTextField24 = new JTextField();
			jTextField24.setPreferredSize(new java.awt.Dimension(80, 20));
			jTextField24.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					String contents = jTextField24.getText().trim();
					log.debug("  contents: " + contents);
					try
					{
						Integer.parseInt(contents);
						jTextField24.setForeground(Color.BLACK);
					}
					catch (NumberFormatException nfe)
					{
						jTextField24.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField24;
	}

	private JButton getJButton3()
	{
		if (jButton3 == null)
		{
			jButton3 = new JButton();
			jButton3.setText("Add quantile");
			jButton3.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String contents = jTextField24.getText().trim();
					try
					{
						state.quantiles.addQuantile(Integer.parseInt(contents));
						getJListModel1().addElement(contents);
						jTextField24.setText("");
					}
					catch (NumberFormatException nfe)
					{
						// this really shouldn't happen
						log.debug("did not add the value '" + contents + "' to the quantile means");
					}
				}
			});
		}
		return jButton3;
	}

	private JButton getJButton4()
	{
		if (jButton4 == null)
		{
			jButton4 = new JButton();
			jButton4.setText("Remove quantile");
			jButton4.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{

					if (jList1.getSelectedIndices().length > 0)
					{
						int r;
						if ((jList1.getSelectedIndices().length == 1))
							getJTextField24().setText(jList1.getSelectedValue().toString());

						for (int i = jList1.getSelectedIndices().length - 1; i >= 0; i--)
						{
							r = jList1.getSelectedIndices()[i];
							getJListModel1().remove(r);
							state.quantiles.removeQuantile(r);
						}
					}
				}
			});
		}
		return jButton4;
	}

	private JButton getJButton5()
	{
		if (jButton5 == null)
		{
			jButton5 = new JButton();
			jButton5.setText("Analyze");
			jButton5.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					if (!allInputsAreValid())
					{
						showError("You must change the red fields to valid inputs.");
						return;
					}

					log.debug("'run analysis' button clicked");

					getJButton5().setEnabled(false);
					gui.setEnabled(false);
					progressMonitor = new ProgressMonitor(rootPane, "Performing Calculations", "", 0, 100);
					progressMonitor.setMillisToPopup(5);
					progressMonitor.setMillisToDecideToPopup(150);
					progressMonitor.setProgress(0);

					try
					{
						sim = Simulator.getSimulator(state);
					}
					catch (IllegalArgumentException iae)
					{
						showError(iae.getMessage());
						jButton5.setEnabled(true);
						gui.setEnabled(true);
						return;
					}

					task = new FutureTask<Object>(sim);

					(new Thread(task)).start();

					timer = new Timer(500, new ActionListener()
					{

						private int i = 0;

						public void actionPerformed(ActionEvent evt)
						{
							log.debug("time waking" + i++);

							progressMonitor.setProgress(sim.getProgress());
							progressMonitor.setNote(sim.getDescription());

							if (progressMonitor.isCanceled())
							{
								boolean result = task.cancel(true);
								log.debug("cancel result = " + result);
							}

							if (task.isDone() || task.isCancelled())
							{
								timer.stop();
								progressMonitor.close();

								getJButton5().setEnabled(true);
								gui.setEnabled(true);
								progressMonitor.close();
								try
								{
									showMessage(gui.finished(task));
								}
								catch(Exception e)
								{
									showError(e.getMessage());
								}
								gui.setEnabled(true);
								jButton5.setEnabled(true);
							}
						}
					});

					timer.start();

				}
			});
		}
		return jButton5;
	}
	
	private JButton getJButton6()
	{
		if (jButton6 == null)
		{
			jButton6 = new JButton();
			jButton6.setText("Load parameters...");
			jButton6.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					Preferences prefs = Preferences.userNodeForPackage(getClass());
					JFileChooser chooser = new JFileChooser(prefs.get("paramfilename", System.getProperty("user.dir")));
					chooser.setFileFilter(chooser.getAcceptAllFileFilter());
					int returnVal = chooser.showOpenDialog(rootPane);
					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						String inputLine = chooser.getSelectedFile().getAbsolutePath();
	
						try
						{
							state = new State(inputLine);
							synchUIWithNewState();
						}
						catch(Exception e2) //assume the constructor threw an error
						{
							log.error(e2);
							JOptionPane.showMessageDialog(rootPane, "That file is not a valid input file.",
									"Bad parameter file", JOptionPane.ERROR_MESSAGE);
						}
						prefs.put("paramfilename", (new File(inputLine)).getParentFile().getAbsolutePath());
					}
				}
			});
		}
		return jButton6;
	}


	private JTextField getJTextField2()
	{
		if (jTextField2 == null)
		{
			jTextField2 = new JTextField();
			jTextField2.setPreferredSize(new java.awt.Dimension(60, 20));

			jTextField2.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					log.debug("CHANGING the number of estimated points");
					String contents = jTextField2.getText().trim();
					log.debug("  contents: " + contents);
					try
					{
						int value = Integer.parseInt(contents);
						jTextField2.setForeground(Color.BLACK);
						state.support.setResolution(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField2.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField2;
	}


	private JTextField getJTextField4()
	{
		if (jTextField4 == null)
		{
			jTextField4 = new JTextField();
			jTextField4.setEnabled(false);
			jTextField4.setEditable(false);
			jTextField4.setPreferredSize(new Dimension(150, 20));
			jTextField4.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					jTextField4.setText(state.bandwidth.getFilename());
				}
			});
			jTextField4.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					state.bandwidth.setFilename(jTextField4.getText().trim());
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});

		}
		return jTextField4;
	}

	private JTextField getJTextField7()
	{
		if (jTextField7 == null)
		{
			jTextField7 = new JTextField();
			jTextField7.setEnabled(true);
			jTextField7.setEditable(false);
			jTextField7.setPreferredSize(new Dimension(150, 20));
			jTextField7.addFocusListener(new java.awt.event.FocusAdapter()
			{
				public void focusLost(java.awt.event.FocusEvent e)
				{
					jTextField7.setText(state.bandwidth.getWeightsfile());
				}
			});
			
			jTextField7.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					state.bandwidth.setWeightsfile(jTextField7.getText().trim());
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});

		}
		return jTextField7;
	}

	private JComboBox getJComboBox6()
	{
		if (jComboBox6 == null)
		{
			jComboBox6 = new JComboBox();
			jComboBox6.addItem("Bandwidth");
			jComboBox6.addItem("Standard Deviation");
			jComboBox6.addItem("Percentage");

			jComboBox6.addItemListener(new java.awt.event.ItemListener()
			{
				public void itemStateChanged(java.awt.event.ItemEvent e)
				{
					if (e.getStateChange() == ItemEvent.SELECTED)
					{
						if(!synching)
						{
							state.support.setM(Support.MType.values()[jComboBox6.getSelectedIndex()]);
							synchUIWithState();
						}
					}
				}
			});
		}
		return jComboBox6;
	}


	private JRadioButton getJRadioButton4()
	{
		if (jRadioButton4 == null)
		{
			jRadioButton4 = new JRadioButton();
			jRadioButton4.setText("Population size");
			jRadioButton4.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton4.isSelected())
					{
						state.synthesizer.setUseRepRatio(false);
						synchUIWithState();
					}
				}
			});
		}
		return jRadioButton4;
	}

	private JRadioButton getJRadioButton5()
	{
		if (jRadioButton5 == null)
		{
			jRadioButton5 = new JRadioButton();
			jRadioButton5.setText("Representation ratio");
			jRadioButton5.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton5.isSelected())
					{
						state.synthesizer.setUseRepRatio(true);
						synchUIWithState();
					}
				}
			});
		}
		return jRadioButton5;
	}

	private JTextField getJTextField6()
	{
		if (jTextField6 == null)
		{
			jTextField6 = new JTextField();
			jTextField6.getDocument().addDocumentListener(new DocumentListener()
			{

				private void update(DocumentEvent e)
				{
					String contents = jTextField6.getText().trim();
					try
					{
						double value = Double.parseDouble(contents);
						jTextField6.setForeground(Color.BLACK);
						state.synthesizer.setRepRatio(value);
					}
					catch (NumberFormatException nfe)
					{
						jTextField6.setForeground(Color.RED);
					}
				}

				public void insertUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void removeUpdate(DocumentEvent e)
				{
					update(e);
				}

				public void changedUpdate(DocumentEvent e)
				{
					update(e);
				}

			});
		}
		return jTextField6;
	}

	private JButton getJButton9()
	{
		if (jButton9 == null)
		{
			jButton9 = new JButton();
			jButton9.setText("Remove poverty line");
			jButton9.addMouseListener(new java.awt.event.MouseAdapter()
			{
				public void mouseClicked(java.awt.event.MouseEvent e)
				{

					if (jList.getSelectedIndices().length > 0)
					{
						int r;
						if ((jList.getSelectedIndices().length == 1))
							getJTextField1().setText(jList.getSelectedValue().toString());

						for (int i = jList.getSelectedIndices().length - 1; i >= 0; i--)
						{
							r = jList.getSelectedIndices()[i];
							getJListModel().remove(r);
							state.povertyLines.removePovertyLine(r);
						}
					}
				}
			});
		}
		return jButton9;
	}

	//TODO MARKER panels start here
	

	private JPanel getJPanel8() //buffer
	{
		if (jPanel8 == null)
		{
			jPanel8 = new JPanel();
		}
		return jPanel8;
	}
	private JPanel getJPanel1() //buffer
	{
		if (jPanel1 == null)
		{
			jPanel1 = new JPanel();
		}
		return jPanel1;
	}
	
	private JPanel getJPanel3() //buffer
	{
		if (jPanel3 == null)
		{
			jPanel3 = new JPanel();
		}
		return jPanel3;
	}


	private JPanel getJPanel()
	{
		if (jPanel == null)
		{
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.gridy = 2;
			gridBagConstraints6.gridheight = 2;
			gridBagConstraints6.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints6.anchor = java.awt.GridBagConstraints.NORTH;
			gridBagConstraints6.insets = new java.awt.Insets(3, 7, 3, 7);
			gridBagConstraints6.weightx = 0.0D;
			gridBagConstraints6.weighty = 4.0D;
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 3;
			gridBagConstraints7.insets = new java.awt.Insets(3, 7, 3, 7);
			gridBagConstraints7.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints7.weightx = 1.0D;
			gridBagConstraints7.weighty = 0.0D;
			gridBagConstraints7.gridwidth = 2;
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 2;
			gridBagConstraints8.weightx = 1.7D;
			gridBagConstraints8.weighty = 5.0D;
			gridBagConstraints8.insets = new java.awt.Insets(3, 7, 3, 7);
			gridBagConstraints8.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints8.gridwidth = 2;
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 5;
			gridBagConstraints2.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints2.insets = new java.awt.Insets(3, 7, 3, 7);
			gridBagConstraints2.weightx = 0.0D;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 5;
			gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints3.insets = new java.awt.Insets(3, 7, 3, 7);
			gridBagConstraints3.weightx = 0.0D;
			gridBagConstraints18.gridx = 0;
			gridBagConstraints18.gridy = 0;
			gridBagConstraints18.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints18.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints18.insets = new java.awt.Insets(7, 7, 3, 7);
			gridBagConstraints18.weightx = 1.0D;
			gridBagConstraints18.weighty = 0.0D;
			gridBagConstraints18.gridwidth = 1;
			gridBagConstraints19.gridx = 2;
			gridBagConstraints19.gridy = 0;
			gridBagConstraints19.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints19.weightx = 0.0D;
			gridBagConstraints19.weighty = 0.0D;
			gridBagConstraints19.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints19.insets = new java.awt.Insets(7, 7, 3, 7);
			gridBagConstraints19.ipadx = 5;
			gridBagConstraints19.ipady = 5;
			gridBagConstraints19.gridwidth = 1;

			jPanel.add(getSourcePanel(), gridBagConstraints18);
			jPanel.add(getInputFilePanel(), gridBagConstraints19);
			jPanel.add(getDensityPanel(), gridBagConstraints6);
			jPanel.add(getPopulationsPanel(), gridBagConstraints7);
			jPanel.add(getIndicatorsPanel(), gridBagConstraints8);
			jPanel.add(getJButton5(), gridBagConstraints2);
			jPanel.add(getJButton6(), gridBagConstraints3);
		}
		return jPanel;
	}

	private JPanel getDensityPanel()
	{
		if (densityPanel == null)
		{
			GridBagConstraints gridBagConstraints48 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints99 = new GridBagConstraints();
			densityPanel = new JPanel();
			densityPanel.setLayout(new GridBagLayout());
			densityPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kernel Density Estimation",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.BOLD,
							14), null));
			densityPanel.setPreferredSize(new java.awt.Dimension(200, 200));
			densityPanel.setEnabled(false);
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.weightx = 1.0D;
			gridBagConstraints15.weighty = 0.0D;
			gridBagConstraints15.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints15.ipadx = 20;
			gridBagConstraints15.ipady = 20;
			densityPanel.add(getDensityKernelPanel(), gridBagConstraints15);
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridy = 3;
			gridBagConstraints21.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints21.weightx = 1.0D;
			gridBagConstraints21.weighty = 0.0D;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints21.ipadx = 5;
			gridBagConstraints21.ipady = 5;
			gridBagConstraints21.gridwidth = 2;

			gridBagConstraints99.gridx = 0;
			gridBagConstraints99.gridy = 4;
			gridBagConstraints99.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints99.weightx = 1.0D;
			gridBagConstraints99.weighty = 0.0D;
			gridBagConstraints99.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints99.ipadx = 5;
			gridBagConstraints99.ipady = 5;
			gridBagConstraints99.gridwidth = 2;
			
			gridBagConstraints22.gridx = 0;
			gridBagConstraints22.gridy = 5;
			gridBagConstraints22.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints22.weightx = 1.0D;
			gridBagConstraints22.weighty = 0.0D;
			gridBagConstraints22.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints22.ipadx = 20;
			gridBagConstraints22.ipady = 20;
			gridBagConstraints22.gridwidth = 2;
			gridBagConstraints23.gridx = 0;
			gridBagConstraints23.gridy = 6;
			gridBagConstraints23.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints23.weightx = 1.0D;
			gridBagConstraints23.weighty = 0.0D;
			gridBagConstraints23.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints23.ipadx = 20;
			gridBagConstraints23.ipady = 20;
			gridBagConstraints23.gridwidth = 2;
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.gridy = 2;
			gridBagConstraints14.gridheight = 1;
			gridBagConstraints14.weightx = 1.0D;
			gridBagConstraints14.weighty = 0.0D;
			gridBagConstraints14.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints14.ipadx = 20;
			gridBagConstraints14.ipady = 20;
			gridBagConstraints48.gridx = 0;
			gridBagConstraints48.gridy = 7;
			gridBagConstraints48.weighty = 1.0D;
			gridBagConstraints48.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints48.weightx = 1.0D;
			gridBagConstraints48.anchor = java.awt.GridBagConstraints.CENTER;

			densityPanel.add(getDensityBandwidthPanel(), gridBagConstraints21);
			densityPanel.add(getDensityBandwidthWeightsPanel(), gridBagConstraints99);
			densityPanel.add(getDensitySupportPanel(), gridBagConstraints22);
			densityPanel.add(getDensityReflectionPanel(), gridBagConstraints23);
			densityPanel.add(getDensityNumberEstimatesPanel(), gridBagConstraints14);
			densityPanel.add(getJPanel1(), gridBagConstraints48);
		}
		return densityPanel;
	}

	private JPanel getPopulationsPanel()
	{
		if (populationsPanel == null)
		{
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			TitledBorder titledBorder1 = javax.swing.BorderFactory.createTitledBorder(null,
					"Synthetic Population Generation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			populationsPanel = new JPanel();
			populationsPanel.setLayout(new GridBagLayout());
			populationsPanel.setBorder(titledBorder1);
			gridBagConstraints26.gridx = 0;
			gridBagConstraints26.gridy = 0;
			gridBagConstraints26.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints26.weightx = 1.0D;
			gridBagConstraints26.weighty = 0.0D;
			titledBorder1.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.gridy = 2;
			gridBagConstraints12.weightx = 1.0D;
			gridBagConstraints12.weighty = 0.0D;
			gridBagConstraints12.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints25.gridx = 1;
			gridBagConstraints25.gridy = 2;
			gridBagConstraints25.weightx = 5.0D;
			gridBagConstraints25.weighty = 0.0D;
			gridBagConstraints25.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints31.gridx = 1;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.fill = java.awt.GridBagConstraints.BOTH;
			populationsPanel.add(getPopulationsSizePanel(), gridBagConstraints12);
			populationsPanel.add(getPopulationsGeneratorPanel(), gridBagConstraints25);
		}
		return populationsPanel;
	}

	private JPanel getIndicatorsPanel()
	{
		if (indicatorsPanel == null)
		{
			TitledBorder titledBorder2 = javax.swing.BorderFactory.createTitledBorder(null, "Indicator Selection",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder2.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));
			
			GridBagConstraints gridBagConstraints110 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			GridBagConstraints gridBagConstraintsx = new GridBagConstraints();
			indicatorsPanel = new JPanel();
			indicatorsPanel.setLayout(new GridBagLayout());
			indicatorsPanel.setPreferredSize(new java.awt.Dimension(200, 100));
			indicatorsPanel.setBorder(titledBorder2);
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			gridBagConstraints4.weighty = 1.0D;
			gridBagConstraints4.weightx = 1.0D;
			gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
			
			gridBagConstraints61.gridx = 1;
			gridBagConstraints61.gridy = 1;
			gridBagConstraints61.weightx = 1.0D;
			gridBagConstraints61.weighty = 1.0D;
			gridBagConstraints61.fill = java.awt.GridBagConstraints.BOTH;
			
			gridBagConstraints110.gridx = 1;
			gridBagConstraints110.gridy = 0;
			gridBagConstraints110.weighty = 1.0D;
			gridBagConstraints110.weightx = 1.0D;
			gridBagConstraints110.fill = java.awt.GridBagConstraints.BOTH;
			
			gridBagConstraintsx.gridx = 0;
			gridBagConstraintsx.gridy = 1;
			gridBagConstraintsx.weighty = 1.0D;
			gridBagConstraintsx.weightx = 1.0D;
			gridBagConstraintsx.fill = java.awt.GridBagConstraints.BOTH;
			
			indicatorsPanel.add(getIndicatorsPovertyPanel(), gridBagConstraints4);
			indicatorsPanel.add(getIndicatorsQuantilePanel(), gridBagConstraints61);
			indicatorsPanel.add(getIndicatorsInequalityPanel(), gridBagConstraints110);
			indicatorsPanel.add(getZeroFilterPanel(), gridBagConstraintsx);
		}
		return indicatorsPanel;
	}

	private JPanel getZeroFilterPanel()
	{
		if (zeroFilterPanel == null)
		{
			TitledBorder titledBorder2 = javax.swing.BorderFactory.createTitledBorder(null, "Filtering",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder2.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));

			
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			zeroFilterPanel = new JPanel();
			zeroFilterPanel.setLayout(new GridBagLayout());
			zeroFilterPanel.setBorder(titledBorder2);
			indicatorsPanel.setPreferredSize(new java.awt.Dimension(200, 100));
			
			gridBagConstraints55.gridx = 0;
			gridBagConstraints55.gridy = 0;
			gridBagConstraints55.weighty = 1.0D;
			gridBagConstraints55.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints55.weightx = 1.0D;
			
			gridBagConstraints56.gridx = 0;
			gridBagConstraints56.gridy = 2;
			gridBagConstraints56.weighty = 1.0D;
			gridBagConstraints56.anchor = java.awt.GridBagConstraints.WEST;
			
			gridBagConstraints57.gridx = 0;
			gridBagConstraints57.gridy = 1;
			gridBagConstraints57.weighty = 1.0D;
			gridBagConstraints57.anchor = java.awt.GridBagConstraints.WEST;
			
			gridBagConstraints58.gridx = 0;
			gridBagConstraints58.gridy = 3;
			gridBagConstraints58.weighty = 1.0D;
			gridBagConstraints58.anchor = java.awt.GridBagConstraints.WEST;


			zeroFilterPanel.add(getJRadioButton8(), gridBagConstraints55);
			zeroFilterPanel.add(getJRadioButton10(), gridBagConstraints57);
			zeroFilterPanel.add(getJRadioButton9(), gridBagConstraints56);
			zeroFilterPanel.add(getJRadioButton11(), gridBagConstraints58);
			
			buttonGroup2 = new ButtonGroup();
			buttonGroup2.add(getJRadioButton8());
			buttonGroup2.add(getJRadioButton10());
			buttonGroup2.add(getJRadioButton9());
			buttonGroup2.add(getJRadioButton11());

		}
		return zeroFilterPanel;
	}
	
	private JPanel getSourcePanel()
	{
		if (sourcePanel == null)
		{
			TitledBorder titledBorder3 = javax.swing.BorderFactory.createTitledBorder(null, "Analysis Type",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Courier New",
							java.awt.Font.BOLD, 12), null);
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			sourcePanel = new JPanel();
			sourcePanel.setLayout(new GridBagLayout());
			sourcePanel.setPreferredSize(new java.awt.Dimension(200, 50));
			sourcePanel.setBorder(titledBorder3);
			gridBagConstraints55.gridx = 0;
			gridBagConstraints55.gridy = 0;
			gridBagConstraints55.weighty = 1.0D;
			gridBagConstraints55.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints55.weightx = 1.0D;
			gridBagConstraints56.gridx = 0;
			gridBagConstraints56.gridy = 1;
			gridBagConstraints56.weighty = 1.0D;
			gridBagConstraints56.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints57.gridx = 0;
			gridBagConstraints57.gridy = 2;
			gridBagConstraints57.weighty = 1.0D;
			gridBagConstraints57.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints58.gridx = 0;
			gridBagConstraints58.gridy = 3;
			gridBagConstraints58.weighty = 1.0D;
			gridBagConstraints58.anchor = java.awt.GridBagConstraints.WEST;
			titledBorder3.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));


			sourcePanel.add(getJRadioButton2(), gridBagConstraints55);
			sourcePanel.add(getJRadioButton3(), gridBagConstraints56);
			sourcePanel.add(getJRadioButton6(), gridBagConstraints57);
			sourcePanel.add(getJRadioButton7(), gridBagConstraints58);
			buttonGroup1 = new ButtonGroup();
			buttonGroup1.add(getJRadioButton2());
			buttonGroup1.add(getJRadioButton3());
			buttonGroup1.add(getJRadioButton6());
			buttonGroup1.add(getJRadioButton7());

		}
		return sourcePanel;
	}

	private JRadioButton getJRadioButton6()
	{
		if (jRadioButton6 == null)
		{
			jRadioButton6 = new JRadioButton();
			jRadioButton6.setText("Compute indicators from given populations");
			jRadioButton6.addChangeListener(new javax.swing.event.ChangeListener()
			{
				public void stateChanged(javax.swing.event.ChangeEvent e)
				{
					if (jRadioButton6.isSelected())
					{
						state.analysis.setType(Analysis.Type.indicators); 
						synchUIWithState();
					}
				}
			});
		}
		return jRadioButton6;
	}

	private JPanel getInputFilePanel()
	{
		if (inputFilePanel == null)
		{
			TitledBorder titledBorder4 = javax.swing.BorderFactory.createTitledBorder(null, "Input File(s)",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			jLabel9 = new JLabel();
			jLabel8 = new JLabel();
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints60 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints611 = new GridBagConstraints();
			inputFilePanel = new JPanel();
			inputFilePanel.setLayout(new GridBagLayout());
			inputFilePanel.setPreferredSize(null);
			inputFilePanel.setBorder(titledBorder4);
			gridBagConstraints57.gridx = 0;
			gridBagConstraints57.gridy = 0;
			gridBagConstraints57.weightx = 1.0D;
			gridBagConstraints57.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints57.insets = new java.awt.Insets(5, 0, 5, 10);
			gridBagConstraints57.ipadx = 0;
			gridBagConstraints57.ipady = 0;
			jLabel8.setText("File type:");
			gridBagConstraints58.gridx = 1;
			gridBagConstraints58.gridy = 0;
			gridBagConstraints58.gridwidth = 2;
			gridBagConstraints58.weightx = 4.0D;
			gridBagConstraints58.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints58.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints58.insets = new java.awt.Insets(5, 0, 5, 0);
			gridBagConstraints59.gridx = 0;
			gridBagConstraints59.gridy = 1;
			gridBagConstraints59.weightx = 1.0D;
			gridBagConstraints59.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints59.insets = new java.awt.Insets(5, 0, 5, 10);
			jLabel9.setText("File name:");
			gridBagConstraints60.gridx = 1;
			gridBagConstraints60.gridy = 1;
			gridBagConstraints60.weightx = 4.0D;
			gridBagConstraints60.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints60.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints60.insets = new java.awt.Insets(5, 0, 5, 0);
			gridBagConstraints611.gridx = 2;
			gridBagConstraints611.gridy = 1;
			gridBagConstraints611.weightx = 1.0D;
			gridBagConstraints611.insets = new java.awt.Insets(5, 0, 5, 0);
			gridBagConstraints611.ipady = 0;
			titledBorder4.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));

			inputFilePanel.add(jLabel8, gridBagConstraints57);
			inputFilePanel.add(getJComboBox1(), gridBagConstraints58);
			inputFilePanel.add(jLabel9, gridBagConstraints59);
			inputFilePanel.add(getJTextField10(), gridBagConstraints60);
			inputFilePanel.add(getJButton1(), gridBagConstraints611);
		}
		return inputFilePanel;
	}

	private JPanel getDensityBandwidthPanel()
	{
		if (densityBandwidthPanel == null)
		{
			TitledBorder titledBorder7 = javax.swing.BorderFactory.createTitledBorder(null, "Bandwidth",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			jLabel12 = new JLabel();
			jLabel11 = new JLabel();
			jLabel10 = new JLabel();
			densityBandwidthPanel = new JPanel();
			densityBandwidthPanel.setLayout(new GridBagLayout());
			jLabel10.setText("Type:");
			jLabel12.setText("File name:");
			jLabel12.setEnabled(false);
			jLabel11.setText("Value:");
			jLabel11.setEnabled(false);
			titledBorder7.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			densityBandwidthPanel.setBorder(titledBorder7);

			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();

			gridBagConstraints62.gridx = 0;
			gridBagConstraints62.gridy = 0;
			gridBagConstraints62.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints62.insets = new java.awt.Insets(5, 0, 0, 10);

			gridBagConstraints64.gridx = 0;
			gridBagConstraints64.gridy = 1;
			gridBagConstraints64.insets = new java.awt.Insets(5, 0, 0, 10);
			gridBagConstraints64.anchor = java.awt.GridBagConstraints.EAST;
			
			gridBagConstraints66.gridx = 0;
			gridBagConstraints66.gridy = 2;
			gridBagConstraints66.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints66.insets = new java.awt.Insets(5, 0, 0, 10);


			gridBagConstraints63.gridx = 1;
			gridBagConstraints63.gridy = 0;
			gridBagConstraints63.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints63.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints65.gridx = 1;
			gridBagConstraints65.gridy = 1;
			gridBagConstraints65.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints65.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 2;
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints68.gridx = 2;
			gridBagConstraints68.gridy = 2;
			gridBagConstraints68.insets = new java.awt.Insets(5, 0, 0, 0);
			gridBagConstraints68.fill = java.awt.GridBagConstraints.HORIZONTAL;
			
			
			
			densityBandwidthPanel.add(jLabel10, gridBagConstraints62);
			densityBandwidthPanel.add(getJComboBox3(), gridBagConstraints63);
			densityBandwidthPanel.add(jLabel11, gridBagConstraints64);
			densityBandwidthPanel.add(jLabel12, gridBagConstraints66);
			densityBandwidthPanel.add(getJButton2(), gridBagConstraints68);
			densityBandwidthPanel.add(getJTextField11(), gridBagConstraints65);
			densityBandwidthPanel.add(getJTextField4(), gridBagConstraints11);
		}
		return densityBandwidthPanel;
	}
	
	private JPanel getDensityBandwidthWeightsPanel()
	{
		if (densityBandwidthWeightsPanel == null)
		{
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			TitledBorder titledBorder7 = javax.swing.BorderFactory.createTitledBorder(null, "Bandwidth Weights",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			jLabel31 = new JLabel();
			jLabel32 = new JLabel();
			jLabel31.setText("Type:");
			jLabel32.setText("File name:");
			jLabel32.setEnabled(false);

			densityBandwidthWeightsPanel = new JPanel();
			densityBandwidthWeightsPanel.setLayout(new GridBagLayout());
			densityBandwidthWeightsPanel.setBorder(titledBorder7);
			titledBorder7.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			
			gridBagConstraints62.gridx = 0;
			gridBagConstraints62.gridy = 0;
			gridBagConstraints62.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints62.insets = new java.awt.Insets(5, 0, 0, 10);
			
			gridBagConstraints66.gridx = 0;
			gridBagConstraints66.gridy = 1;
			gridBagConstraints66.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints66.insets = new java.awt.Insets(5, 0, 0, 10);
			
			gridBagConstraints63.gridx = 1;
			gridBagConstraints63.gridy = 0;
			gridBagConstraints63.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints63.insets = new java.awt.Insets(5, 0, 0, 0);

			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints68.gridx = 2;
			gridBagConstraints68.gridy = 1;
			gridBagConstraints68.insets = new java.awt.Insets(5, 0, 0, 0);
			gridBagConstraints68.fill = java.awt.GridBagConstraints.HORIZONTAL;
			
			
			
			densityBandwidthWeightsPanel.add(jLabel31, gridBagConstraints62);
			densityBandwidthWeightsPanel.add(getJComboBox7(), gridBagConstraints63);
			densityBandwidthWeightsPanel.add(jLabel32, gridBagConstraints66);
			densityBandwidthWeightsPanel.add(getJButton10(), gridBagConstraints68);
			densityBandwidthWeightsPanel.add(getJTextField7(), gridBagConstraints11);
		}
		return densityBandwidthWeightsPanel;
	}

	private JPanel getDensitySupportPanel()
	{
		if (densitySupportPanel == null)
		{
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			TitledBorder titledBorder8 = javax.swing.BorderFactory.createTitledBorder(null, "Density Support",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			densitySupportPanel = new JPanel();
			densitySupportPanel.setBorder(titledBorder8);
			titledBorder8.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			jLabel16 = new JLabel();
			jLabel15 = new JLabel();
			jLabel14 = new JLabel();
			jLabel13 = new JLabel();
			jLabel19 = new JLabel();
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints74 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints76 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints78 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			densitySupportPanel.setLayout(new GridBagLayout());
			
			gridBagConstraints69.gridx = 0;
			gridBagConstraints69.gridy = 0;
			gridBagConstraints69.insets = new java.awt.Insets(0, 0, 0, 10);
			gridBagConstraints69.anchor = java.awt.GridBagConstraints.EAST;
			jLabel13.setText("Type:");
			
			gridBagConstraints71.gridx = 0;
			gridBagConstraints71.gridy = 1;
			gridBagConstraints71.insets = new java.awt.Insets(5, 0, 0, 10);
			gridBagConstraints71.anchor = java.awt.GridBagConstraints.EAST;
			jLabel14.setText("M:");
			
			gridBagConstraints73.gridx = 0;
			gridBagConstraints73.gridy = 2;
			gridBagConstraints73.insets = new java.awt.Insets(5, 0, 0, 10);
			gridBagConstraints73.anchor = java.awt.GridBagConstraints.EAST;
			jLabel15.setText("%:");
			
			gridBagConstraints74.gridx = 0;
			gridBagConstraints74.gridy = 3;
			gridBagConstraints74.insets = new java.awt.Insets(5, 0, 0, 10);
			gridBagConstraints74.anchor = java.awt.GridBagConstraints.EAST;
			jLabel16.setText("Theta:");
			
			gridBagConstraints77.gridx = 0;
			gridBagConstraints77.gridy = 4;
			gridBagConstraints77.insets = new java.awt.Insets(5, 0, 0, 10);
			gridBagConstraints77.anchor = java.awt.GridBagConstraints.EAST;
			jLabel19.setText("File name:");
			
			gridBagConstraints70.gridx = 1;
			gridBagConstraints70.gridy = 0;
			gridBagConstraints70.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints70.insets = new java.awt.Insets(0, 0, 0, 0);
			
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints75.gridx = 1;
			gridBagConstraints75.gridy = 2;
			gridBagConstraints75.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints75.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints76.gridx = 1;
			gridBagConstraints76.gridy = 3;
			gridBagConstraints76.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints76.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints78.gridx = 1;
			gridBagConstraints78.gridy = 4;
			gridBagConstraints78.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints78.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints79.gridx = 2;
			gridBagConstraints79.gridy = 4;
			gridBagConstraints79.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints79.insets = new java.awt.Insets(5, 0, 0, 0);
			
			densitySupportPanel.add(jLabel13, gridBagConstraints69);
			densitySupportPanel.add(getJComboBox4(), gridBagConstraints70);
			densitySupportPanel.add(jLabel14, gridBagConstraints71);
			densitySupportPanel.add(jLabel15, gridBagConstraints73);
			densitySupportPanel.add(jLabel16, gridBagConstraints74);
			densitySupportPanel.add(jLabel19, gridBagConstraints77);
			densitySupportPanel.add(getJTextField13(), gridBagConstraints75);
			densitySupportPanel.add(getJTextField14(), gridBagConstraints76);
			densitySupportPanel.add(getJComboBox6(), gridBagConstraints3);
			densitySupportPanel.add(getJTextField3(), gridBagConstraints78);
			densitySupportPanel.add(getJButton7(), gridBagConstraints79);
			//TODO add the file chooser here!
		}
		return densitySupportPanel;
	}

	private JPanel getDensityReflectionPanel()
	{
		if (densityReflectionPanel == null)
		{
			TitledBorder titledBorder9 = javax.swing.BorderFactory.createTitledBorder(null, "Reflection",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			jLabel18 = new JLabel();
			jLabel17 = new JLabel();
			jLabel18.setText("Phi:");
			jLabel18.setEnabled(false);
			jLabel17.setText("Type:");

			densityReflectionPanel = new JPanel();
			densityReflectionPanel.setLayout(new GridBagLayout());
			densityReflectionPanel.setBorder(titledBorder9);
			titledBorder9.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			
			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints78 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			
			gridBagConstraints77.gridx = 0;
			gridBagConstraints77.gridy = 0;
			gridBagConstraints77.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints77.insets = new java.awt.Insets(5, 0, 0, 10);
			
			gridBagConstraints78.gridx = 1;
			gridBagConstraints78.gridy = 0;
			gridBagConstraints78.weightx = 0.0D;
			gridBagConstraints78.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints78.insets = new java.awt.Insets(5, 0, 0, 0);
			
			gridBagConstraints79.gridx = 0;
			gridBagConstraints79.gridy = 1;
			gridBagConstraints79.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints79.insets = new java.awt.Insets(5, 0, 0, 10);
			
			gridBagConstraints80.gridx = 1;
			gridBagConstraints80.gridy = 1;
			gridBagConstraints80.weightx = 0.0D;
			gridBagConstraints80.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints80.insets = new java.awt.Insets(5, 0, 0, 0);
			
			densityReflectionPanel.add(jLabel17, gridBagConstraints77);
			densityReflectionPanel.add(getJComboBox5(), gridBagConstraints78);
			densityReflectionPanel.add(jLabel18, gridBagConstraints79);
			densityReflectionPanel.add(getJTextField15(), gridBagConstraints80);
		}
		return densityReflectionPanel;
	}

	private JPanel getDensityKernelPanel()
	{
		if (densityKernelPanel == null)
		{
			jLabel30 = new JLabel();
			TitledBorder titledBorder5 = javax.swing.BorderFactory.createTitledBorder(null, "Kernel",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			densityKernelPanel = new JPanel();
			densityKernelPanel.setBorder(titledBorder5);
			titledBorder5.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			jLabel30.setText("Type:");
			densityKernelPanel.add(jLabel30, null);
			densityKernelPanel.add(getJComboBox2(), null);
		}
		return densityKernelPanel;
	}

	private JPanel getDensityNumberEstimatesPanel()
	{
		if (densityNumberEstimatesPanel == null)
		{
			TitledBorder titledBorder6 = javax.swing.BorderFactory.createTitledBorder(null, "# Points",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", java.awt.Font.PLAIN,
							11), null);
			densityNumberEstimatesPanel = new JPanel();
			densityNumberEstimatesPanel.setBorder(titledBorder6);
			titledBorder6.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			densityNumberEstimatesPanel.add(getJTextField2(), null);
		}
		return densityNumberEstimatesPanel;
	}

	
	private JPanel getPopulationsSizePanel()
	{
		if (populationsSizePanel == null)
		{
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints28 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			TitledBorder titledBorder31 = javax.swing.BorderFactory.createTitledBorder(null, "x",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			populationsSizePanel = new JPanel();
			populationsSizePanel.setLayout(new GridBagLayout());
			populationsSizePanel.setBorder(titledBorder31);
			titledBorder31.setTitle("Size");
			titledBorder31.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			gridBagConstraints32.gridx = 1;
			gridBagConstraints32.gridy = 0;
			gridBagConstraints32.weightx = 1.0;
			gridBagConstraints32.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.gridy = 0;
			gridBagConstraints16.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints28.gridx = 0;
			gridBagConstraints28.gridy = 1;
			gridBagConstraints28.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints34.gridx = 1;
			gridBagConstraints34.gridy = 1;
			gridBagConstraints34.weightx = 1.0;
			gridBagConstraints34.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints42.gridx = 2;
			gridBagConstraints42.gridy = 1;

			populationsSizePanel.add(getJTextField(), gridBagConstraints32);
			populationsSizePanel.add(getJRadioButton4(), gridBagConstraints16);
			populationsSizePanel.add(getJRadioButton5(), gridBagConstraints28);
			populationsSizePanel.add(getJTextField6(), gridBagConstraints34);
			
			populationsSizeButtons = new ButtonGroup();
			populationsSizeButtons.add(getJRadioButton4());
			populationsSizeButtons.add(getJRadioButton5());


		}
		return populationsSizePanel;
	}

	private JPanel getPopulationsGeneratorPanel()
	{
		if (populationsGeneratorPanel == null)
		{
			GridBagConstraints gridBagConstraints141 = new GridBagConstraints();
			TitledBorder titledBorder30 = javax.swing.BorderFactory.createTitledBorder(null, "x",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			populationsGeneratorPanel = new JPanel();
			populationsGeneratorPanel.setLayout(new GridBagLayout());
			populationsGeneratorPanel.setBorder(titledBorder30);
			titledBorder30.setTitle("Generator");
			titledBorder30.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			gridBagConstraints141.gridx = 0;
			gridBagConstraints141.gridy = 0;
			gridBagConstraints141.weightx = 1.0;
			gridBagConstraints141.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints81.gridx = 0;
			gridBagConstraints81.gridy = 1;
			populationsGeneratorPanel.add(getJComboBox(), gridBagConstraints141);
			populationsGeneratorPanel.add(getInterpolationCheckbox(), gridBagConstraints81);
		}
		return populationsGeneratorPanel;
	}


	private JPanel getIndicatorsPovertyPanel()
	{
		if (indicatorsPovertyPanel == null)
		{
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints261 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints271 = new GridBagConstraints();

			indicatorsPovertyPanel = new JPanel();
			indicatorsPovertyPanel.setLayout(new GridBagLayout());
			TitledBorder titledBorder10 = javax.swing.BorderFactory.createTitledBorder(null, "Poverty Indicators",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder10.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			indicatorsPovertyPanel.setBorder(titledBorder10);

			gridBagConstraints261.gridx = 0;
			gridBagConstraints261.gridy = 0;
			gridBagConstraints261.weightx = 1.0D;
			gridBagConstraints261.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints271.gridx = 0;
			gridBagConstraints271.gridy = 1;
			gridBagConstraints271.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints271.weighty = 0.0D;
			gridBagConstraints271.weightx = 1.0D;
			gridBagConstraints17.gridx = 0;
			gridBagConstraints17.gridy = 2;
			gridBagConstraints17.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints17.weighty = 1.0D;
			gridBagConstraints17.weightx = 1.0D;
			indicatorsPovertyPanel.add(getJPanel21(), gridBagConstraints261);
			indicatorsPovertyPanel.add(getPovertyIndicatorSelectorPanel(), gridBagConstraints271);
			indicatorsPovertyPanel.add(getJPanel8(), gridBagConstraints17);

		}
		return indicatorsPovertyPanel;
	}


	private JPanel getIndicatorsInequalityPanel()
	{
		if (indicatorsInequalityPanel == null)
		{
			indicatorsInequalityPanel = new JPanel();
			indicatorsInequalityPanel.setLayout(new GridBagLayout());
			TitledBorder titledBorder11 = javax.swing.BorderFactory.createTitledBorder(null, "Inequality Indicators",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder11.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			indicatorsInequalityPanel.setBorder(titledBorder11);

			inequalityCheckBoxs = new JCheckBox[state.indicators.getNumberOfInequalityIndicators()];
			inequalityTextFields = new JTextField[state.indicators.getNumberOfInequalityIndicators()];

			GridBagConstraints inequalityCheckBoxConstraints;
			GridBagConstraints inequalityTextFieldConstraints;

			for (int i = 0; i < inequalityCheckBoxs.length; i++)
			{
				inequalityCheckBoxs[i] = new JCheckBox();
				inequalityCheckBoxs[i].setText(state.indicators.getInequalityIndicator(i).getName());
				if (state.indicators.getInequalityIndicator(i).isActive())
					inequalityCheckBoxs[i].setSelected(true);
				else
					inequalityCheckBoxs[i].setSelected(false);

				inequalityCheckBoxConstraints = new GridBagConstraints();
				inequalityCheckBoxConstraints.gridx = 0;
				inequalityCheckBoxConstraints.gridy = i;
				inequalityCheckBoxConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
				inequalityCheckBoxConstraints.anchor = java.awt.GridBagConstraints.WEST;

				indicatorsInequalityPanel.add(inequalityCheckBoxs[i], inequalityCheckBoxConstraints);


				if (state.indicators.getInequalityIndicator(i).getParameterName() != null)
				{
					inequalityTextFields[i] = new JTextField();
					inequalityTextFields[i].setPreferredSize(new Dimension(50, 20));
					inequalityTextFields[i].setMinimumSize(inequalityTextFields[i].getPreferredSize());	

					inequalityTextFields[i].getDocument().addDocumentListener(
							new ParameterFieldListener(state.indicators.getInequalityIndicator(i), inequalityTextFields[i]));

					inequalityTextFieldConstraints = new GridBagConstraints();
					inequalityTextFieldConstraints.gridx = 2;
					inequalityTextFieldConstraints.gridy = i;
					inequalityTextFieldConstraints.weightx = 1.0;
					inequalityTextFieldConstraints.fill = java.awt.GridBagConstraints.NONE;
					inequalityTextFieldConstraints.anchor = java.awt.GridBagConstraints.EAST;

				
					indicatorsInequalityPanel.add(inequalityTextFields[i], inequalityTextFieldConstraints);
				}
				else
				{
					inequalityTextFields[i] = null;
				}

				inequalityCheckBoxs[i].addChangeListener(new CheckBoxSwitcher(inequalityCheckBoxs[i],
						inequalityTextFields[i], state.indicators.getInequalityIndicator(i)));
			}

			// add filler panel at bottom to push the checkboxes to the top of
			// the panel
			JPanel filler = new JPanel();
			GridBagConstraints fillerConstraints = new GridBagConstraints();
			fillerConstraints.gridx = 0;
			fillerConstraints.gridy = inequalityCheckBoxs.length;
			fillerConstraints.gridwidth = 3;
			fillerConstraints.fill = GridBagConstraints.BOTH;
			fillerConstraints.weighty = 1;
			indicatorsInequalityPanel.add(filler, fillerConstraints);
		}
		return indicatorsInequalityPanel;
	}


	private JPanel getIndicatorsQuantilePanel()
	{
		if (indicatorsQuantilePanel == null)
		{
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			TitledBorder titledBorder13 = javax.swing.BorderFactory.createTitledBorder(null, "Quantile Means",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints108 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints107 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints106 = new GridBagConstraints();
			indicatorsQuantilePanel = new JPanel();
			indicatorsQuantilePanel.setLayout(new GridBagLayout());
			gridBagConstraints106.gridx = 0;
			gridBagConstraints106.gridy = 0;
			gridBagConstraints106.weightx = 1.0D;
			gridBagConstraints106.weighty = 1.0;
			gridBagConstraints106.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints106.gridheight = 4;
			gridBagConstraints107.gridx = 1;
			gridBagConstraints107.gridy = 0;
			gridBagConstraints107.weightx = 1.0D;
			gridBagConstraints107.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints107.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints107.weighty = 0.0D;
			gridBagConstraints107.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints108.gridx = 2;
			gridBagConstraints108.gridy = 0;
			gridBagConstraints108.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints108.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints108.insets = new java.awt.Insets(5, 0, 5, 5);
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 2;
			gridBagConstraints1.weighty = 0.0D;
			gridBagConstraints1.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints1.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints1.gridwidth = 2;
			indicatorsQuantilePanel.setBorder(titledBorder13);
			titledBorder13.setTitleFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 11));
			gridBagConstraints111.gridx = 1;
			gridBagConstraints111.gridy = 3;
			gridBagConstraints111.fill = java.awt.GridBagConstraints.BOTH;
			gridBagConstraints111.weighty = 1.0D;

			jScrollPane2 =new JScrollPane(getJList1());
			jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			
			indicatorsQuantilePanel.add(jScrollPane2, gridBagConstraints106);
			indicatorsQuantilePanel.add(getJTextField24(), gridBagConstraints107);
			indicatorsQuantilePanel.add(getJPanel3(), gridBagConstraints111);

			Dimension d = jScrollPane2.getSize(new Dimension());
			gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
			indicatorsQuantilePanel.add(getJButton3(), gridBagConstraints108);
			indicatorsQuantilePanel.add(getJButton4(), gridBagConstraints1);
			d.width += 60;
			jScrollPane2.setPreferredSize(d);
			jScrollPane2.setMinimumSize(d);

		}
		return indicatorsQuantilePanel;
	}
	
	private JPanel getJPanel21()
	{
		if (jPanel21 == null)
		{
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			jPanel21 = new JPanel();
			jPanel21.setLayout(new GridBagLayout());
			
			jScrollPane1 =new JScrollPane(getJList());
			jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			jScrollPane1.getVerticalScrollBar().setSize(1,1);
			Dimension d = jScrollPane1.getSize(new Dimension());
			d.height += 50;
			d.width += 60;
			jScrollPane1.setPreferredSize(d);
			jScrollPane1.setMinimumSize(d);
			
			gridBagConstraints38.gridx = 0;
			gridBagConstraints38.gridy = 0;
			gridBagConstraints38.gridwidth = 1;
			gridBagConstraints38.gridheight = 3;
			gridBagConstraints38.weightx = 9.0D;
			gridBagConstraints38.weighty = 1.0;
			gridBagConstraints38.fill = java.awt.GridBagConstraints.BOTH;
			jPanel21.add(jScrollPane1, gridBagConstraints38);

			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints5.gridwidth = 2;
			gridBagConstraints5.anchor = java.awt.GridBagConstraints.EAST;
			gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
			jPanel21.add(getJButton9(), gridBagConstraints5);

			gridBagConstraints40.gridx = 2;
			gridBagConstraints40.gridy = 0;
			gridBagConstraints40.gridheight = 1;
			gridBagConstraints40.insets = new java.awt.Insets(5, 0, 5, 5);
			gridBagConstraints40.weightx = 0.0D;
			gridBagConstraints40.fill = java.awt.GridBagConstraints.NONE;
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.EAST;
			jPanel21.add(getJButton(), gridBagConstraints40);
			
			gridBagConstraints39.gridx = 1;
			gridBagConstraints39.gridy = 0;
			gridBagConstraints39.gridheight = 1;
			gridBagConstraints39.weightx = 3.0D;
			gridBagConstraints39.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints39.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints39.weighty = 0.0D;
			gridBagConstraints39.anchor = java.awt.GridBagConstraints.EAST;
			jPanel21.add(getJTextField1(), gridBagConstraints39);
		}
		return jPanel21;
	}


	private JPanel getPovertyIndicatorSelectorPanel()
	{
		if (povertyIndicatorSelectorPanel == null)
		{

			povertyIndicatorSelectorPanel = new JPanel();
			povertyIndicatorSelectorPanel.setLayout(new GridBagLayout());

			povertyCheckBoxs = new JCheckBox[state.indicators.getNumberOfPovertyIndicators()];
			povertyTextFields = new JTextField[state.indicators.getNumberOfPovertyIndicators()];

			GridBagConstraints povertyCheckBoxConstraints;
			GridBagConstraints povertyTextFieldConstraints;

			for (int i = 0; i < povertyCheckBoxs.length; i++)
			{
				povertyCheckBoxs[i] = new JCheckBox();
				povertyCheckBoxs[i].setText(state.indicators.getPovertyIndicator(i).getName());
				if (state.indicators.getPovertyIndicator(i).isActive())
					povertyCheckBoxs[i].setSelected(true);
				else
					povertyCheckBoxs[i].setSelected(false);

				povertyCheckBoxConstraints = new GridBagConstraints();
				povertyCheckBoxConstraints.gridx = 0;
				povertyCheckBoxConstraints.gridy = i;
				povertyCheckBoxConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
				povertyCheckBoxConstraints.anchor = java.awt.GridBagConstraints.WEST;

				povertyIndicatorSelectorPanel.add(povertyCheckBoxs[i], povertyCheckBoxConstraints);


				if (state.indicators.getPovertyIndicator(i).getParameterName() != null)
				{
					povertyTextFields[i] = new JTextField();
					povertyTextFields[i].setPreferredSize(new Dimension(50, 20));
					povertyTextFields[i].setMinimumSize(povertyTextFields[i].getPreferredSize());

					povertyTextFieldConstraints = new GridBagConstraints();
					povertyTextFieldConstraints.gridx = 2;
					povertyTextFieldConstraints.gridy = i;
					povertyTextFieldConstraints.weightx = 1.0;
					povertyTextFieldConstraints.fill = java.awt.GridBagConstraints.NONE;
					povertyTextFieldConstraints.anchor = java.awt.GridBagConstraints.EAST;


					povertyTextFields[i].getDocument().addDocumentListener(
							new ParameterFieldListener(state.indicators.getPovertyIndicator(i), povertyTextFields[i]));

					povertyIndicatorSelectorPanel.add(povertyTextFields[i], povertyTextFieldConstraints);
				}
				else
				{
					povertyTextFields[i] = null;
				}

				povertyCheckBoxs[i].addChangeListener(new CheckBoxSwitcher(povertyCheckBoxs[i], povertyTextFields[i], 
						state.indicators.getPovertyIndicator(i)));
				
			}

		}
		return povertyIndicatorSelectorPanel;
	}





} // @jve:decl-index=0:visual-constraint="13,6"

