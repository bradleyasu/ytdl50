package com.hexotic.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class XTextField extends JTextField{

	public XTextField(){
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)){
					try {
						String data = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
						updateTextData(data);
					} catch (Exception ex){}
				}
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}

	private void updateTextData(String text){
		this.setText(text);
	}
	
	public void paintComponent(Graphics g) {  
	     super.paintComponent(g);
	     g.setColor(new Color(24,24,24));
	     g.drawLine(0,getHeight()-1, getWidth(), getHeight()-1);
	     g.drawLine(0,getHeight()-5, 0, getHeight()-1);
	     g.drawLine(getWidth()-1,getHeight()-5, getWidth()-1, getHeight()-1);
	}   
}
