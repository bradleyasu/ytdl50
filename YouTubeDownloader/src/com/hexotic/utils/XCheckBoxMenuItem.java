package com.hexotic.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
public class XCheckBoxMenuItem extends JCheckBoxMenuItem{

	public XCheckBoxMenuItem(String text){
		super(text);
		this.setOpaque(false);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setPreferredSize(new Dimension(110,20));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Color[] colors = { new Color(255,34,102),
				 			new Color(255,85,51),
				 			new Color(255,68,85),
		 					new Color(255,0,153),
		 };

         for(int i = 0; i < getHeight(); i++){
        	 Random rand = new Random();
        	 int index = rand.nextInt(colors.length);
        	 g.setColor(colors[index]);
        	 g.fillRect(getWidth()-2, i*2, 2, 2);
         }


	 }
	
}
