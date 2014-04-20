package com.hexotic.utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class XComboBox extends JComboBox{
	public XComboBox(Object[] items){
		super(items);
		this.setPreferredSize(new Dimension(150,25));
		this.setOpaque(false);
		this.setBackground(Color.WHITE);
		this.setUI(new XComboUI());
		this.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
	}
}


class XComboUI extends BasicComboBoxUI {
    @Override
	protected ComboPopup createPopup() {
        BasicComboPopup bcp = (BasicComboPopup) super.createPopup();
 
        // set the border around the popup
        bcp.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
 
        //there is an inner border around the list, insdie the scroller
        //    it can be set thus:
        // JList list = bcp.getList();
        // list.setBorder(BorderFactory.createLineBorder(Color.green, 2));
 
        return bcp;
    }
    
    @Override
	protected JButton createArrowButton() {
        return new ComboButton();
    }
}

class ComboButton extends JButton{
	public ComboButton(){
		setFont(new Font("Arial", Font.BOLD, 1));
		setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
		setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(100, 23));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);

         Graphics2D g2 = (Graphics2D) g.create();
         g2.setPaint(new GradientPaint(new Point(0, 0), new Color(0xefefef), new Point(0,
                 getHeight()), new Color(0xe1e1e1)));         
         g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0,0);
         
         g2.setPaint(new Color(0xababab));
         
         for(int i = 0; i < 3; i++){
        	 g2.fillRect(this.getWidth()/2-2,4+(i*5)+(1*i), 5,5);
         }
         
         
         g2.dispose();
	 }
}