package com.hexotic.v2.gui.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXCollapsiblePane;

import com.hexotic.v2.gui.theme.Theme;

/**
 * This class provides a neat sliding sidebar thing where
 * all of the traditional "File" menu options will be located
 * This is where the user will find all the configurable options
 * 
 * @author Bradley Sheets
 *
 */
public class Sidebar extends JXCollapsiblePane {

	public Sidebar() {
		this.setCollapsed(false);
		this.setAnimated(true);
		this.setDirection(Direction.RIGHT);
		this.setContentPane(new SideBarPanel());
	}
	
	public void toggle() {
		this.setCollapsed(!this.isCollapsed());
	}
	
	
	class SideBarPanel extends JPanel {
		public SideBarPanel() {
			this.setBackground(Theme.DARK);
			this.setPreferredSize(new Dimension(200, 200));
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			int width = 10;
			
			GradientPaint gp1 = new GradientPaint(width, width, new Color(0,0,0,0), 0, width, Theme.DARK_SHADOW, true);
		    g2d.setPaint(gp1);
			g2d.fillRect(getWidth()-width, 0, width, getHeight());

		}
		
	}
}
