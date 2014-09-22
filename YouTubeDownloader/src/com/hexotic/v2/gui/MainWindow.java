package com.hexotic.v2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import com.hexotic.cons.Constants;
import com.hexotic.gui.FooterAnimation;
import com.hexotic.gui.TopPanel;
import com.hexotic.lib.resource.Resources;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.lib.util.WinOps;
import com.hexotic.v2.console.Console;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.gui.primary.DownloadContainer;
import com.hexotic.v2.gui.theme.Theme;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 4600079762459133631L;
	private JInternalFrame main;
	private Console console;
	
	public MainWindow(){
		JDesktopPane desktop = new JDesktopPane();
		this.setContentPane(desktop);
		this.setTitle(Constants.PROG_NAME+" "+Constants.VERSION+" - "+Constants.COMPANY_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(Resources.getInstance().getImage("icon.png"));
		
		createMain();
		createConsole();
		
		desktop.add(main);
		desktop.add(console);
		main.setVisible(true);
		console.setVisible(false);
		
		pack();
		this.setSize(new Dimension(1000, 700));
		WinOps.centreWindow(this);
		this.setVisible(true);
		
		this.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		    	main.setSize(e.getComponent().getWidth()-16, e.getComponent().getHeight()-38);
		    	console.setLocation(4,e.getComponent().getHeight()-console.getHeight()-42);
		    }
			@Override
			public void componentHidden(ComponentEvent e) {
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			@Override
			public void componentShown(ComponentEvent e) {
			}
		});
		
		desktop.setBackground(Theme.MAIN_BACKGROUND);
		
//		main.setFrameIcon(new ImageIcon(Resources.getInstance().getImage("icon_small.png")));
//		
//		try {
//			main.setIcon(true);
//		} catch (PropertyVetoException e1) {
//		}
	}

	
	private void createMain() {
		main = new JInternalFrame();
		main.setLayout(new BorderLayout());
		
		main.add(new TopPanel(), BorderLayout.NORTH);
		
		JScrollPane downloads= new JScrollPane(new DownloadContainer());
		downloads.getVerticalScrollBar().setUI(new SimpleScroller());
		downloads.getVerticalScrollBar().setPreferredSize(new Dimension(5,5));
		downloads.getVerticalScrollBar().setUnitIncrement(25);
		downloads.setBorder(BorderFactory.createEmptyBorder());
		main.add(downloads, BorderLayout.CENTER);
		main.add(new FooterAnimation(), BorderLayout.SOUTH);
		main.setSize(1000, 700);
		main.setBorder(BorderFactory.createEmptyBorder());
		// Remove the title bar
		((BasicInternalFrameUI)main.getUI()).setNorthPane(null);
		
		Log.getInstance().debug(this, "Main Window Created");
	}
	
	private void createConsole() {
		console = new Console();
		Log.getInstance().setConsole(console);
		
		// Set the console to always be on top
		console.getLayeredPane().setLayer(console, JLayeredPane.POPUP_LAYER.intValue());
	
		
		Log.getInstance().debug(this, "Console Window Created");
	}
	
	
	public static void main(String[] args){
		Log.getInstance().debug(new MainWindow(), "Welcome To " + Constants.PROG_NAME+" "+Constants.VERSION);
	}
}
