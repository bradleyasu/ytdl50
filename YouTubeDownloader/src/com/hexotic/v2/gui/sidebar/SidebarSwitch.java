package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;

import javax.swing.JLabel;

import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.lib.switches.SwitchListener;
import com.hexotic.v2.gui.theme.Theme;

public class SidebarSwitch extends SidebarItem {

	private BasicSwitch basicSwitch;

	public SidebarSwitch(int index, String option, String on, String off) {
		
		this.setIndex(index);
		basicSwitch = new BasicSwitch(on, off, 100, 20, 0);
		basicSwitch.setForeground(Theme.DARK);
		basicSwitch.setBackground(Theme.MAIN_COLOR_FOUR);
		basicSwitch.setFont(Theme.SWITCH_FONT);
		JLabel label = new JLabel(option);
		label.setFont(Theme.CONTROL_BAR_FONT);
		label.setForeground(Theme.MAIN_BACKGROUND);
		label.setPreferredSize(new Dimension(75, 20));
		this.add(label);
		this.add(basicSwitch);
	}

	public void setState(boolean state){
		basicSwitch.setState(state);
	}
	public void addSwitchListener(SwitchListener listener){
		basicSwitch.addSwitchListener(listener);
	}
	
}
