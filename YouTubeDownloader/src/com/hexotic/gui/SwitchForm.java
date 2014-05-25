package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.lib.switches.SwitchEvent;
import com.hexotic.lib.switches.SwitchListener;
/**
 * This is a Swith Control view switching between "Video" and "MP3" mode
 * 
 * @author Bradley Sheets
 */

public class SwitchForm extends JPanel{
	private boolean optionActivated = false;
	private JLabel label;
	public SwitchForm(String option, boolean activated){
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		label = new JLabel(option);
		label.setPreferredSize(new Dimension(334, 30));
						
		this.add(label, BorderLayout.CENTER);
		BasicSwitch sw = new BasicSwitch("nope", "yep", 100, 25, 10);
		sw.setBackground(new Color(0xFF5533));
		if(activated){
			sw.setState(true);
			optionActivated = true;
		}
		
		sw.addSwitchListener(new SwitchListener(){
			@Override
			public void switchTriggered(SwitchEvent arg0) {
				if(arg0.getState() == SwitchEvent.ON){
					optionActivated = true;
				} else {
					optionActivated = false;
				}
					
			}
		});
		this.add(sw, BorderLayout.EAST);
//		this.add(new YTSwitch(mode));
	}
	
	public boolean isSet(){
		return optionActivated;
	}
	
}
