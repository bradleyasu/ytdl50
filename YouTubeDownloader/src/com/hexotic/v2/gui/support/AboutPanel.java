package com.hexotic.v2.gui.support;

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.hexotic.cons.Constants;
import com.hexotic.lib.resource.Resources;
import com.hexotic.v2.gui.theme.Theme;

public class AboutPanel extends JPanel {

	public AboutPanel() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(600, 400));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		GradientPaint paint = new GradientPaint(0, 0, Theme.CONTROL_BAR_BACKGROUND, 0, getHeight(), Theme.CONTROL_BAR_BORDER);
		g2d.setPaint(paint);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		g2d.drawImage(Resources.getInstance().getImage("icon_small.png"), 20, 0, 32,32, null);
		
		g2d.setFont(Theme.CONTROL_BAR_FONT.deriveFont(18.0f));
		g2d.setColor(Theme.DARK);
		g2d.drawString(Constants.PROG_NAME, 60, 20);
	}

}
