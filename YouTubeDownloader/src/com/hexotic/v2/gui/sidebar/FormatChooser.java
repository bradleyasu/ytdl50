package com.hexotic.v2.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.lib.ui.panels.FlipPanel;
import com.hexotic.v2.gui.components.ModernMenu;
import com.hexotic.v2.gui.components.ModernMenuItem;
import com.hexotic.v2.gui.components.ModernMenuItemListener;
import com.hexotic.v2.gui.theme.Theme;

public class FormatChooser extends JPanel{

	private int width = 525;
	private int height = 200;
	private int tab = 0;
	private FlipPanel flipper;
	private ModernMenu menu;
	private String audioFormat = "";
	private String videoFormat = "";
	
	public FormatChooser() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(new BorderLayout());
		
		menu = new ModernMenu();
		ModernMenuItem vidItem = new ModernMenuItem("Video");
		ModernMenuItem audItem = new ModernMenuItem("Audio");
		menu.addMenuItem(vidItem);
		menu.addMenuItem(audItem);
		
		
		vidItem.addMenuItemListener(new ModernMenuItemListener(){
			@Override
			public void itemSelected(String label, ModernMenuItem item) {
				if(tab == 1){
					flipper.flip();
					tab = 0;
				}
			}
		});
		
		audItem.addMenuItemListener(new ModernMenuItemListener(){
			@Override
			public void itemSelected(String label, ModernMenuItem item) {
				if(tab == 0){
					flipper.flip();
					tab = 1;
				}
			}
		});
		
		
		this.add(menu, BorderLayout.NORTH);
		
		flipper = new FlipPanel(getVideoPanel(), getAudioPanel());
		flipper.setDirection(FlipPanel.LEFT);
		this.add(flipper, BorderLayout.CENTER);
		this.add(getFooterPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel getFooterPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		SoftButton saveButton = new SoftButton("Save Selected Formats");
		saveButton.setBackgroundColor(Theme.DARK);
		saveButton.setFont(new Font("Arial", Font.BOLD, 12));
		saveButton.setArc(4);
		saveButton.setPreferredSize(new Dimension(200, 20));
		
		panel.add(saveButton);
		return panel;
	}
	
	private JPanel getAudioPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		panel.setLayout(new AnimatedGridLayout(true));
		
		panel.add(new FormatButton("MP3"));
		panel.add(new FormatButton("M4A"));
		panel.add(new FormatButton("WAV"));
		panel.add(new FormatButton("AAC"));
		panel.add(new FormatButton("Opus"));
		panel.add(new FormatButton("Vorbis"));
		
		return panel;
	}
	
	private JPanel getVideoPanel() {
		JPanel panel = new JPanel();
		panel.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		panel.setLayout(new AnimatedGridLayout(true));
		panel.add(new FormatButton("MP4"));
		panel.add(new FormatButton("FLV"));
		panel.add(new FormatButton("OGG"));
		panel.add(new FormatButton("WEBM"));
		panel.add(new FormatButton("MKV"));
		panel.add(new FormatButton("AVI"));
		
		return panel;
	}
	
	private class FormatButton extends JPanel implements Comparable<FormatButton>{
		
		private boolean selected = false;
		private String format = "";
		
		public FormatButton(String text) {
			format = text;
			this.setPreferredSize(new Dimension(80, 30));
			this.setBackground(Theme.DOWNLOAD_ITEM_BACKGROUND);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.addMouseListener(new MouseListener(){
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
					selected = true;
					revalidate();
					repaint();
				}
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2d.setFont(Theme.CONTROL_BAR_FONT);
			g2d.drawString(format, getWidth()/2-12, getHeight()/2+3);
			
			g2d.setColor(Theme.DOWNLOAD_ITEM_BORDER);
			if(selected){
				g2d.setColor(Theme.MAIN_COLOR_FIVE);
			}
			g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
			
		}

		public String getFormat() {
			return format;
		}
		@Override
		public int compareTo(FormatButton b) {
			return this.format.compareTo(b.getFormat());
		}
	}
}
