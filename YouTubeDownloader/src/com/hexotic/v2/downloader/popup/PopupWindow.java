package com.hexotic.v2.downloader.popup;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class PopupWindow extends JInternalFrame{

	private GlassPane glass;
	
	public PopupWindow() {
		super("Hexotic Software", false, // resizable
				false, // closable
				true, // maximizable
				false);// iconifiable

		// Default Size
		this.setOpaque(false);
		glass = new GlassPane();
		// pssh, who needs borders
		// this.setBorder(BorderFactory.createEmptyBorder());
		this.setBorder(BorderFactory.createEmptyBorder());
		((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
		this.setBackground(new Color(0,0,0,0));
		this.add(glass);
		
	}
	
	public void setPrompt(JPanel panel) {
		glass.setPrompt(panel);
	}
	

}
