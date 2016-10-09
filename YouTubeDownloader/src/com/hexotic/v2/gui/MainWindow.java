package com.hexotic.v2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.zip.ZipException;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import com.hexotic.cons.Constants;
import com.hexotic.lib.exceptions.ResourceException;
import com.hexotic.lib.resource.FileInstall;
import com.hexotic.lib.resource.Resources;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.lib.util.WinOps;
import com.hexotic.utils.Settings;
import com.hexotic.v2.commandbuilder.CommandBuilder;
import com.hexotic.v2.console.Console;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.downloader.Downloader;
import com.hexotic.v2.downloader.popup.PopupFactory;
import com.hexotic.v2.downloader.popup.PopupWindow;
import com.hexotic.v2.gui.downloadbar.DownloadBar;
import com.hexotic.v2.gui.downloadbar.DownloadBarListener;
import com.hexotic.v2.gui.primary.DownloadContainer;
import com.hexotic.v2.gui.sidebar.Sidebar;
import com.hexotic.v2.gui.support.ReleaseNotes;
import com.hexotic.v2.gui.support.Updater;
import com.hexotic.v2.gui.theme.Theme;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 4600079762459133631L;
	private JInternalFrame main;
	private Console console;
	private Sidebar sidebar;
	private DownloadBar downloadBar;
	private PopupWindow overlay;
	private DownloadContainer downloadContainer;

	public MainWindow() {
		JDesktopPane desktop = new JDesktopPane();
		this.setContentPane(desktop);
		this.setTitle(Constants.PROG_NAME + " " + Constants.VERSION + " - " + Constants.COMPANY_NAME);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			this.setIconImage(Resources.getInstance().getImage("icon.png"));
		} catch (ResourceException e1) { }

		createMain();
		createConsole();
		createOverlay();
		
		desktop.add(main);
		desktop.add(console);
		desktop.add(overlay);
	
		main.setVisible(true);
		console.setVisible(false);
		pack();
		this.setSize(new Dimension(1000, 700));
		WinOps.centreWindow(this);
		this.setVisible(true);

		this.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				console.setLocation(e.getComponent().getWidth() - console.getWidth() - 20, e.getComponent().getHeight() - console.getHeight() - 42);
				
				int targetWidth = e.getComponent().getWidth() - 16;
				int targetHeight = e.getComponent().getHeight() - 38;
				main.setSize(targetWidth, targetHeight);
				main.setLocation(0,0);
				overlay.setSize(targetWidth, targetHeight);
				overlay.setLocation(0,0);
				
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

		desktop.setBackground(Theme.DARK_SHADOW);
		
		// main.setFrameIcon(new
		// ImageIcon(Resources.getInstance().getImage("icon_small.png")));
		//
		// try {
		// main.setIcon(true);
		// } catch (PropertyVetoException e1) {
		// }
		//sidebar.toggle();
		new Thread(downloadBar).start();
		// Check version number
		checkVersion();
		
		
		// Check for updates
		checkForUpdates();
		
		// Quick commandbuilder call which will auto-load files.  we could wait, but why not take care of it now
		CommandBuilder.builder();
	}

	private void createMain() {
		main = new JInternalFrame();

		JPanel app = new JPanel(new BorderLayout());
		
		main.setLayout(new BorderLayout());
		sidebar = new Sidebar();
		main.add(sidebar, BorderLayout.WEST);
		
		downloadBar = new DownloadBar();
		app.add(downloadBar, BorderLayout.NORTH);

		downloadBar.addDownloadBarListener(new DownloadBarListener(){
			@Override
			public void inputEntered(String input){
				// Process Special Commands
				if(input.startsWith("/")){
					if(input.contains("console")){
						console.setVisible(!console.isVisible());
					} else if(input.contains("supported")){ 
						console.setVisible(true);
						Downloader downloader = new Downloader();
						downloader.getSupported();
					} else if(input.contains("update")){ 
						console.setVisible(true);
						Downloader downloader = new Downloader();
						downloader.update();
					}else if (input.contains("overlay")){
						overlay.setVisible(true);
						JPanel panel = new JPanel();
						panel.setPreferredSize(new Dimension(500, 200));
						panel.setBackground(Color.WHITE);
						overlay.setPrompt(panel);
						
					}
				} else {
					if(downloadContainer.isYoutubePlaylist(input)){
						try {
							downloadContainer.addPlaylist(input);
						} catch (Exception e) {
							Log.getInstance().error(this, "Failed to download playlist", e);
						}
					} else {
						downloadContainer.addDownload(input);
					}
				}
			}
		});
		downloadContainer = new DownloadContainer();
		JScrollPane downloads = new JScrollPane(downloadContainer);
		
		
		downloads.getVerticalScrollBar().setUI(new SimpleScroller());
		downloads.getVerticalScrollBar().setPreferredSize(new Dimension(5, 5));
		downloads.getVerticalScrollBar().setUnitIncrement(25);
		downloads.setBorder(BorderFactory.createEmptyBorder());
		app.add(downloads, BorderLayout.CENTER);
		
		main.add(app, BorderLayout.CENTER);
		main.setSize(1000, 700);
		main.setBorder(BorderFactory.createEmptyBorder());
		
		main.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e) {
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				if(e.getX() < 10 && sidebar.isCollapsed()) {
					sidebar.toggle();
				}
				if(e.getX() > 200 && e.getX() < 210 && !sidebar.isCollapsed()) {
					sidebar.toggle();
				}
			}
		});
		
		// Remove the title bar
		((BasicInternalFrameUI) main.getUI()).setNorthPane(null);

		Log.getInstance().debug(this, "Main Window Created");
		
	}

	private void createConsole() {
		console = new Console();
		Log.getInstance().setConsole(console);

		// Set the console to always be on top
		console.getLayeredPane().setLayer(console, JLayeredPane.POPUP_LAYER.intValue());

		Log.getInstance().debug(this, "Console Window Created");
	}
	
	private void checkForUpdates() {
		Log.getInstance().debug(this, "Checking Youtube-dl for updates");
		Downloader downloader = new Downloader();
		downloader.update();

		Log.getInstance().debug(this, "Checking Youtube Downloader front-end for udpates");
		new Thread(new Runnable(){
			public void run(){
				new Updater();
			}
		}).start();
			
	}

	
	private void createOverlay() {
		overlay = PopupFactory.getPopupWindow();

		// Set the overlay to always be on top
		overlay.getLayeredPane().setLayer(overlay, JLayeredPane.POPUP_LAYER.intValue());

		Log.getInstance().debug(this, "Overlay Window Created");
	}
	
	public static void main(String[] args) {
		Log.getInstance().debug(new MainWindow(), "Welcome To " + Constants.PROG_NAME + " " + Constants.VERSION);
	}
	
	
	private void checkVersion(){
		boolean showRelease = false;
		String release = Settings.getInstance().getProperty("release", null); 
		if (release != null){
			if (!release.equals(Constants.VERSION)){
				showRelease = true;
			}
		} else {
			showRelease = true;
		}
		
		if (showRelease){
			PopupFactory.getPopupWindow().setPrompt(new ReleaseNotes());
			Settings.getInstance().saveProperty("release", Constants.VERSION); 
			
			updateExecs();
		}
		
	}
	
	private void updateExecs() {
		
		String execs = getExecPath();
		Log.getInstance().debug(this, "Updating execs...");
		try {
			Resources.getInstance().installFile("youtube-dl.exe", execs+"youtube-dl.exe");
			Log.getInstance().debug(this, "youtube-dl Updated");
			
			Resources.getInstance().installFile("ffmpeg.exe", execs+"ffmpeg.exe");
			Log.getInstance().debug(this, "FFMpeg Updated");
			
			Resources.getInstance().installFile("ffplay.exe", execs+"ffplay.exe");
			Log.getInstance().debug(this, "FFPlay Updated");
			
			Resources.getInstance().installFile("ffprobe.exe", execs+"ffprobe.exe");
			Log.getInstance().debug(this, "FFProbe Updated");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getExecPath() {
		String OS = System.getProperty("os.name").toUpperCase();
		String downloader;
		if (OS.contains("WIN")) {
			downloader = System.getenv("APPDATA") + "\\YouTube Downloader 5.0\\execs\\";
		} else if (OS.contains("MAC")) {
			downloader = System.getProperty("user.home") + "/Library/Application " + "Support";
		} else if (OS.contains("NUX")) {
			downloader = System.getProperty("user.home");
		} else {
			downloader = "";
		}
		return downloader;
	}
}
