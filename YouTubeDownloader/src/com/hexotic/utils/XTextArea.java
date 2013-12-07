package com.hexotic.utils;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

public class XTextArea extends JTextArea{

	public XTextArea(String defaultText){
		this.setText(defaultText);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setPreferredSize(new Dimension(400,55));
		this.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
	}
	
}
