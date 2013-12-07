package com.hexotic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class FooterAnimation extends JPanel{

	public FooterAnimation(){
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(200, 2));
	}
	
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Color[] colors = { new Color(255,34,102),
				 			new Color(255,85,51),
				 			new Color(255,68,85),
		 					new Color(255,0,153),
		 };

         for(int i = 0; i < getWidth(); i++){
        	 Random rand = new Random();
        	 int index = rand.nextInt(colors.length);
        	 g.setColor(colors[index]);
        	 g.fillRect(i*10, 0, 10, 2);
         }


	 }
}
