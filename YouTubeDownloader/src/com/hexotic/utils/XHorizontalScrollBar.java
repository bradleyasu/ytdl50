package com.hexotic.utils;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollBar;

public class XHorizontalScrollBar extends JScrollBar{
	
	public XHorizontalScrollBar(){
		this.setBackground(Color.WHITE);
		this.setForeground(new Color(240,240,240));
		this.setOrientation(HORIZONTAL);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setPreferredSize(new Dimension(getWidth(), 7));
		this.setUnitIncrement(50);
	}

}
