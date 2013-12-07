package com.hexotic.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hexotic.utils.Settings;

public class FormYTDLSwitch extends JPanel{
	private String downloadFormat = "video";
	private JLabel label;
	public FormYTDLSwitch(){
		this.setOpaque(false);
		this.setLayout(new FlowLayout());
		
		String mode = Settings.getInstance().getProperty("defaultDownloadMode");
		if(mode == null){
			mode = "video";
		}
		label = new JLabel("Download my URL's in "+mode+" format by default");
		label.setPreferredSize(new Dimension(270, 30));
						
		this.add(label);
		this.add(new YTSwitch(mode));
	}
	
	public String getDownloadFormat(){
		return downloadFormat;
	}
	
	private class YTSwitch extends JPanel{
		private JLabel switchBtn;
		
		private ClassLoader cldr = this.getClass().getClassLoader();
		private ImageIcon switchAudio = new ImageIcon(cldr.getResource("images/switch_mp3.png"));
		private ImageIcon switchVideo = new ImageIcon(cldr.getResource("images/switch_video.png"));
		public YTSwitch(String mode){
			this.setOpaque(false);
			this.setPreferredSize(new Dimension(100,30));
			if(mode.equals("audio")){
				switchBtn = new JLabel(switchAudio);
				downloadFormat = "audio";
			}else{
				switchBtn = new JLabel(switchVideo);
				downloadFormat = "video";
			}
			switchBtn.addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent arg0) {
					if(downloadFormat.equals("audio")){
						switchBtn.setIcon(switchVideo);
						downloadFormat = "video";
					}else{
						switchBtn.setIcon(switchAudio);
						downloadFormat = "audio";
					}
					label.setText("Download my URL's in "+downloadFormat+" format by default");
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
			
			
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.add(switchBtn);
		}
	}
}
