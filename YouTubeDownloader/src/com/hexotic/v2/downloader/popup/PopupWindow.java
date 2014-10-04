package com.hexotic.v2.downloader.popup;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class PopupWindow extends JInternalFrame {

	private static final long serialVersionUID = -1904050233507642558L;
	private GlassPane glass;

	public PopupWindow() {
		super("Hexotic Software", false, // resizable
				false, // closable
				true, // maximizable
				false);// iconifiable

		// Default Size
		this.setOpaque(false);
		glass = new GlassPane();
		
		// When the user clicks outside of the popup, close the window
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!glass.isPopupFocused()){
					glass.close();
				}
			}
		});

		// pssh, where we're going, we don't need borders
		this.setBorder(BorderFactory.createEmptyBorder());
		((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
		this.setBackground(new Color(0, 0, 0, 0));
		this.add(glass);
	}

	public PopupWindow setPrompt(JPanel panel) {
		this.setVisible(true);
		glass.setPrompt(panel);
		return this;
	}
	
	public void propagateClose() {
		glass.close();
		this.setVisible(false);
	}

}
