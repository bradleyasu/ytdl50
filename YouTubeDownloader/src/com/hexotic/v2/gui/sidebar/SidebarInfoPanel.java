package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.v2.gui.theme.Theme;

public class SidebarInfoPanel extends JPanel{

	private List<SoftButton> buttons = new ArrayList<SoftButton>();
	
	public SidebarInfoPanel() {
		this.setPreferredSize(new Dimension(200, 50));
		this.setOpaque(false);
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		SoftButton aboutBtn = new SoftButton("About");
		SoftButton releaseBtn = new SoftButton("Release");
		SoftButton bugsBtn = new SoftButton("Bugs");
		
		buttons.add(aboutBtn);
		buttons.add(releaseBtn);
		buttons.add(bugsBtn);
		
		for(SoftButton button :buttons){
			button.setPreferredSize(new Dimension(60,30));
			button.setBackgroundColor(Theme.DARK);
			button.setForegroundColor(Theme.MAIN_BACKGROUND);
			button.setFont(Theme.CONTROL_BAR_FONT);
			button.setArc(0);
			
			this.add(button);
		}
		
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
	}
}
