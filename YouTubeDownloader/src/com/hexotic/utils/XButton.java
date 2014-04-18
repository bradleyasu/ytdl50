package com.hexotic.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import com.hexotic.lib.ui.buttons.SoftButton;

public class XButton extends SoftButton{

	private static final long serialVersionUID = 4425782501114150434L;

	public XButton(String text){
		super(text);
		setFont(new Font("Arial", Font.BOLD, 12));
		setBackground(Color.WHITE);
		this.setBackgroundColor(new Color(0x434547));
		this.setForeground(new Color(0xf2f2f2));
		this.setArc(7);
        setPreferredSize(new Dimension(100, 22));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

}
