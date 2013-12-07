package com.hexotic.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class XButton extends JButton{

	public XButton(String text){
		super(text);
		setFont(new Font("Arial", Font.BOLD, 1));
		setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
		setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(100, 23));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}


	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Color[] colors = { new Color(255,34,102),
		 			new Color(255,85,51),
		 			new Color(255,68,85),
					new Color(255,0,153),
		 };
         Graphics2D g2 = (Graphics2D) g.create();
         g2.setPaint(new GradientPaint(new Point(0, 0), new Color(0x515151), new Point(0,
                 getHeight()), new Color(0x121212)));         
         g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0,0);
         g2.setPaint(new Color(0xf4f4f4));
         g2.setFont(new Font("Arial", Font.BOLD, 11));
         g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2.drawString(getText(), 24, 15);
         g2.dispose();
	 }
}
