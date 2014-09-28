package com.hexotic.v2.downloader.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXCollapsiblePane;

public class GlassPane extends JPanel {

	private JXCollapsiblePane content;

	public GlassPane() {
		this.setBackground(new Color(0, 0, 0, 80));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		content = new JXCollapsiblePane();
		content.setAnimated(true);
		content.setCollapsed(true);
		JPanel p = new JPanel(new BorderLayout(0, 0));
		p.add(content);
		p.setOpaque(false);
		this.add(p);
	}

	public void setPrompt(JPanel panel){
		content.setContentPane(panel);
		content.setCollapsed(false);
		
	}

	public void close() {
		content.setCollapsed(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		GradientPaint gp1 = new GradientPaint(0, 0, new Color(0, 0, 0, 175), 0, 50, new Color(0, 0, 0, 0), true);

		g2d.setPaint(gp1);
		g2d.fillRect(0, 0, getWidth(), 50);

	}

}
