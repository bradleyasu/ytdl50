package com.hexotic.v2.gui.components;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexotic.v2.gui.theme.Theme;

public class ModernMenuItem extends JPanel{

	private String label;
	private boolean selected = false;
	private List<ModernMenuItemListener> listeners;
	
	public ModernMenuItem(String label){
		this.label = label;
		this.setPreferredSize(new Dimension(70, 30));
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		listeners = new ArrayList<ModernMenuItemListener>();
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				notifyListeners();
				revalidate();
				repaint();
			}
			
		});
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void addMenuItemListener(ModernMenuItemListener listener) {
		listeners.add(listener);
	}
	
	public void notifyListeners() {
		for(ModernMenuItemListener listener : listeners) {
			listener.itemSelected(label, this);
		}
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setFont(Theme.CONTROL_BAR_FONT.deriveFont(16.0F));
		
		g2d.drawString(label, 12, 15);
		
		g2d.setColor(Theme.MAIN_COLOR_FOUR);
		if(selected){
			g2d.fillRect(0, getHeight()-5, getWidth(), getHeight());
		}
	}
	
}
