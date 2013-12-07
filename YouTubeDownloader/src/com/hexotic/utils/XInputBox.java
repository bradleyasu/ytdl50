package com.hexotic.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class XInputBox extends JTextField{

	public XInputBox(String defaultText){
		this.setText(defaultText);
		this.setPreferredSize(new Dimension(150,25));
		this.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
	}
	
}
