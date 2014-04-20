package com.hexotic.utils;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.UIManager;

import com.hexotic.cons.Constants;
import com.hexotic.gui.MainWindow;
import com.hexotic.gui.ReleaseNotes;

/**
 *  YouTube Downloader 5.0 HD
 * 
 *  Copyright (c) 2013 Hexotic Software.
 *  All rights reserved.
 *  
 *  Redistribution and use in source and binary forms are permitted
 *  provided that the above copyright notice and this paragraph are
 *  duplicated in all such forms and that any documentation,
 *  advertising materials, and other materials related to such
 *  distribution and use acknowledge that the software was developed
 *  by Hexotic Software.  The name of the
 *  Hexotic Software may not be used to endorse or promote products derived
 *  from this software without specific prior written permission.
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *  
 *  @author Bradley Sheets 2013
 * 
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome To "+Constants.PROG_NAME+" "+Constants.VERSION);
		UIManager.put("MenuItem.selectionBackground", new Color(230,230,230));
		UIManager.put("MenuItem.selectionForeground", new Color(0,0,0));
		UIManager.put("Menu.selectionBackground", new Color(230,230,230));
		UIManager.put("Menu.selectionForeground",  new Color(0,0,0));
		UIManager.put("MenuBar.selectionBackground", new Color(230,230,230));
		UIManager.put("MenuBar.selectionForeground",  new Color(0,0,0));
		UIManager.put("PopupMenu.border", BorderFactory.createLineBorder(new Color(210,210,210), 1));
		UIManager.put("MenuItem.border", BorderFactory.createLineBorder(new Color(230,230,230), 1));
		UIManager.put("ProgressBar.background",new Color(255,255,255));  
		UIManager.put("ProgressBar.foreground",new Color(40,40,40)); 
		UIManager.put("ProgressBar.selectionBackground",new Color(100,100,100)); 
		UIManager.put("ProgressBar.selectionForeground",new Color(100,100,100)); 

		UIManager.put("ToolTip.background", new Color(0xffffe0));
		UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(0xffff90)));
		UIManager.put("CheckBoxMenuItem.selectionBackground", new Color(230,230,230));
		
		UIManager.put("CheckBox.foreground", new Color(230,230,230));
		UIManager.put("CheckBox.background", new Color(230,230,230));
		
		UIManager.put("Tree.selectionBackground", new Color(230,230,230));
		UIManager.put("Tree.selectionBorderColor", new Color(210,210,210));
		
		ArrayList<Object> gradients = new ArrayList<Object>(5);
		gradients.add(0.28f);
		gradients.add(0.00f);
		gradients.add(new Color(0xe1e1e1));
		gradients.add(new Color(0xefefef));
		gradients.add(new Color(0xf7f7f7));
		UIManager.put( "ScrollBar.background", new Color(0xe1e1e1) );
		UIManager.put( "ScrollBar.darkShadow", new Color(0xdadada) );	
		UIManager.put( "ScrollBar.highlight", new Color(0xd8d8d8) );
		UIManager.put( "ScrollBar.shadow", new Color(0xd0d0d0) );
		UIManager.put( "ScrollBar.thumbShadow", new Color(0xd0d0d0) );
		UIManager.put( "ScrollBar.thumbHighlight", new Color(0xe7e7e7) );
		UIManager.put( "ScrollBar.gradient", gradients );
		UIManager.put( "control", new Color(0xe1e1e1) );
		
		UIManager.put("CheckBoxMenuItem.checkIcon", new Icon() {
			  @Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
			    Graphics2D g2 = (Graphics2D)g;
			    g2.translate(x,y);
			    ButtonModel m = ((AbstractButton)c).getModel();
			    Color active = new Color(255,68,85);
			    g2.setPaint(m.isSelected()?active:Color.GRAY);
			    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			                        RenderingHints.VALUE_ANTIALIAS_ON);
			    g2.fillOval( 0, 2, 10, 10 );
			    g2.translate(-x,-y);
			  }
			  @Override
			public int getIconWidth()  { return 14; }
			  @Override
			public int getIconHeight() { return 14; }
			});
		new MainWindow();
		new Updater();
	}

}
