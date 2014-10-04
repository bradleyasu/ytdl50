package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.v2.gui.theme.Theme;

public class SidebarButton extends SidebarItem {

	private static final long serialVersionUID = 8278589419017233824L;
	private SoftButton button;

	public SidebarButton(String button, int index) {
		this.setIndex(index);

		this.button = new SoftButton(button);
		this.button.setPreferredSize(new Dimension(185, 20));
		this.button.setBackgroundColor(Theme.DARK);
		this.button.setForegroundColor(Theme.MAIN_BACKGROUND);
		this.button.setFont(Theme.CONTROL_BAR_FONT);
		this.button.setArc(0);

		this.add(this.button);
	}

	public void addActionListener(ActionListener al) {
		button.addActionListener(al);
	}
}
