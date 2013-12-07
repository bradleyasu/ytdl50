package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.hexotic.cons.Constants;
import com.hexotic.utils.CentralDownloadControl;

public class MainWindow extends JFrame{
	
	public MainWindow(){
		this.setTitle(Constants.PROG_NAME+" "+Constants.VERSION+" - "+Constants.COMPANY_NAME);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new TopPanel(), BorderLayout.NORTH);
		this.add(new MainContainer(), BorderLayout.CENTER);
		this.add(new FooterAnimation(), BorderLayout.SOUTH);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL iconPth   = cldr.getResource("images/icon.png");
		this.setIconImage(new ImageIcon(iconPth).getImage());
		
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(1000, 700));
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		this.setLocation(x, y);
		this.setVisible(true);
//		new MessageBox(Constants.MSG_5, "Your internet sucks", "I couldn't detect an internet connection", null);
//					   "Check your internet connection yo.  If everything looks good, then it is my mistake.");
		
		
		// CHECK FOR UPDATES
		try {
			System.out.println(CentralDownloadControl.getInstance().updateDownloader());
		} catch (IOException e) {
			new MessageBox(Constants.MSG_2, "Update Failed", "There was a problem updating core components", e.toString());
		}
	}
	
	
}
