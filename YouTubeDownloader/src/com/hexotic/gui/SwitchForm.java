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
		label.setPreferredSize(new Dimension(334, 20));
						
		this.add(label, BorderLayout.CENTER);
		BasicSwitch sw = new BasicSwitch("OFF", "ON", 90, 20, 2);
		sw.setBackground(new Color(0xffffff));
		sw.setForeground(new Color(0x525252));
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
