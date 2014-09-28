package com.hexotic.v2.gui.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.v2.gui.theme.Theme;

public class SidebarItem extends JPanel implements Comparable<SidebarItem> {

	private BasicSwitch basicSwitch;
	private int index = 0;

	public SidebarItem(int index, String option, String on, String off) {
		this.setBackground(Theme.DARK);
		this.setPreferredSize(new Dimension(199, 50));
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 17));
		this.index = index;
		basicSwitch = new BasicSwitch(on, off, 100, 20, 0);
		basicSwitch.setForeground(Theme.DARK);
		basicSwitch.setBackground(Theme.MAIN_COLOR_FOUR);
		basicSwitch.setFont(Theme.SWITCH_FONT);
		JLabel label = new JLabel(option);
		label.setFont(Theme.CONTROL_BAR_FONT);
		label.setForeground(Theme.MAIN_BACKGROUND);
		label.setPreferredSize(new Dimension(75, 20));
		this.add(label);
		this.add(basicSwitch);
	}

	public int getIndex(){
		return index;
	}
	
	@Override
	public int compareTo(SidebarItem item){
		return getIndex() - item.getIndex();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = 10;

		GradientPaint gp1 = new GradientPaint(width, width, new Color(0, 0, 0, 0), 0, width, Theme.DARK_SHADOW, true);
		g2d.setPaint(gp1);
		g2d.fillRect(getWidth() - width, 0, width, getHeight());

		g2d.setColor(Theme.DARK.brighter());
		g2d.drawLine(0, 0, getWidth(), 0);

		g2d.setColor(Theme.DARK.darker());
		g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

	}
}
