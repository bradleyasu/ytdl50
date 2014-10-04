package com.hexotic.v2.gui.support;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.hexotic.v2.gui.theme.Theme;

public class AboutPanel extends JPanel {

	public AboutPanel() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(600, 400));
	}
}
