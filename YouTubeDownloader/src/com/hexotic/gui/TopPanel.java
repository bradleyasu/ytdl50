package com.hexotic.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class TopPanel extends JPanel{
	
	public TopPanel(){
		this.setLayout(new BorderLayout());
		this.add(new YTMenu(), BorderLayout.NORTH);
		this.add(new UrlPanel(), BorderLayout.CENTER);
	}
}

