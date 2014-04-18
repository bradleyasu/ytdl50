package com.hexotic.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import com.hexotic.utils.CentralDownloadControl;
import com.hexotic.utils.Settings;
import com.hexotic.utils.XCheckBoxMenuItem;
import com.hexotic.utils.XMenu;
import com.hexotic.utils.XMenuItem;

public class YTMenu extends JMenuBar{
	private ClassLoader cldr = this.getClass().getClassLoader();
	public YTMenu(){
		this.setBackground(Color.WHITE);
		this.setOpaque(false);
		this.add(getFileMenu());
		this.add(getOptionsMenu());
		this.add(getAboutMenu());
		this.setBorder(BorderFactory.createEmptyBorder());
	}

	private JMenu getFileMenu(){
		XMenu file = new XMenu("File");

		XMenuItem openList = new XMenuItem("Open List");
		XMenuItem saveList = new XMenuItem("Save List");
		file.add(openList);
		file.add(saveList);
		JSeparator seperator = new JSeparator();
		seperator.setForeground(new Color(210,210,210));
		seperator.setBorder(BorderFactory.createEmptyBorder());
		file.add(seperator);
		

		XMenuItem quit = new XMenuItem("Quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		file.add(quit);
		return file;
	}
	
	private JMenu getOptionsMenu(){
		XMenu options = new XMenu("Options");
		XCheckBoxMenuItem autoDownload = new XCheckBoxMenuItem("Auto Download From Clipboard");
		autoDownload.setPreferredSize(new Dimension(210, 20));
		autoDownload.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				XCheckBoxMenuItem temp = (XCheckBoxMenuItem)e.getSource();
				CentralDownloadControl.getInstance().setDownloadOnCopy(temp.isSelected());
			}
		});
		
		XCheckBoxMenuItem mp3Download = new XCheckBoxMenuItem("Convert To MP3 on Download");
		mp3Download.setPreferredSize(new Dimension(210, 20));
		mp3Download.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				XCheckBoxMenuItem temp = (XCheckBoxMenuItem)e.getSource();
				CentralDownloadControl.getInstance().setDownloadAsMP3(temp.isSelected());
			}
		});
		
		//CHECK DEFAULT STATE FOR DOWNLOADS
		String defaultState = Settings.getInstance().getProperty("defaultDownloadMode");
		if(defaultState != null){
			if(defaultState.equals("audio")){
				mp3Download.setState(true);
				CentralDownloadControl.getInstance().setDownloadAsMP3(mp3Download.isSelected());
			}
		}
		
		JSeparator seperator = new JSeparator();
		seperator.setForeground(new Color(210,210,210));
		seperator.setBorder(BorderFactory.createEmptyBorder());
		
		XMenuItem prefs = new XMenuItem("Custom Settings");
		
		prefs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new OptionsWindow();
			}
		});
		
		options.add(prefs);
		options.add(seperator);
		options.add(autoDownload);
		options.add(mp3Download);
		return options;
	}
	
	private JMenu getAboutMenu(){
		
		
		
		XMenu about = new XMenu("About");
		XMenuItem license = new XMenuItem("About and License");
		license.setPreferredSize(new Dimension(125, 20));
		license.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new AboutBox();
			}
		});

		XMenuItem releaseNotes = new XMenuItem("Release Notes");
		releaseNotes.setPreferredSize(new Dimension(125, 20));
		releaseNotes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new ReleaseNotes();
			}
		});
//		about.add(releaseNotes);
		about.add(license);
		return about;
	}
	
	 protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Color[] colors = { new Color(255,34,102),
				 			new Color(255,85,51),
				 			new Color(255,68,85),
		 					new Color(255,0,153),
		 };
		 int c = 230;
         for(int i = 0; i < getHeight(); i++){
        	 g.setColor(new Color(c, c,c));
        	 g.drawLine(0, i, getWidth(), i);
        	 c += 1;
         }
         g.setColor(new Color(200,200,200));
         g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);

//         for(int i = 0; i < getHeight(); i++){
//        	 Random rand = new Random();
//        	 int index = rand.nextInt(colors.length);
//        	 g.setColor(colors[index]);
//        	 g.fillRect(getWidth()-2, i*2, 2, 2);
//         }

         
         
	 }
}
