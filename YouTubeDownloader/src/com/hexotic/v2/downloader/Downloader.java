package com.hexotic.v2.downloader;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.hexotic.v2.console.Log;

/**
 * This class provides an interface with the open source youtube-dl effort
 * 
 * @author Bradley.Sheets
 * 
 */
public class Downloader {

	/* Holds the single instance of the downloader */
	private static final String UNKNOWN = "UNKNOWN";

	private File downloader;

	/* The process in which the youtube-dl is executed in */
	private Process proc;
	
	private List<DownloadListener> listeners = new ArrayList<DownloadListener>();

	public Downloader() {
		/* Get the YT-DL executable */
		String downloaderPath = getDownloader();
		if (!UNKNOWN.equals(downloaderPath)) {
			downloader = new File(downloaderPath);
		}
	}

	public Image getThumbnailUrl(String url) throws IOException {
		String[] args = {"--get-thumbnail", url };
		String thumbnailUrl = execute(args).trim();
		Log.getInstance().debug(this,"Loaded Image Thumnail: "+thumbnailUrl);
		
		ImageIcon icon = new ImageIcon(new URL(thumbnailUrl));
		return icon.getImage();
	}
	
	
	public String getTitle(String url) throws IOException {
		String[] args = {"--get-title", url };
		String videoTitle = execute(args).trim();
		Log.getInstance().debug(this,"Loaded Video Title: "+videoTitle);
		return videoTitle;
	}

	public void download(String url, boolean audio, String downloadDirectory) throws IOException {
		
		String[] cmd = {"--audio-format", "mp3", "-o", "\""+downloadDirectory+"\\%(title)s.%(ext)s\"",  url};
		execute(cmd);
	}
	
	private String execute(String[] parameters) throws IOException {
		// Build command with parameters passed in
		String[] cmd = new String[parameters.length + 1];
		cmd[0] = downloader.getAbsolutePath();
		for (int i = 0; i < parameters.length; i++) {
			cmd[i + 1] = parameters[i];
		}

		StringBuilder builder = new StringBuilder();
		for(String c : cmd){
			builder.append(c+" ");
		}
		Log.getInstance().debug(this, builder.toString());
		
		// Execute command
		Runtime rt = Runtime.getRuntime();
		proc = rt.exec(cmd);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		String line;

		StringBuilder out = new StringBuilder();
				
		while ((line = stdInput.readLine()) != null) {
			Log.getInstance().debug(this, line);
			out.append(line);
			notifyListeners(line);
		}
		return out.toString();
	}

	public void addDownloadListener(DownloadListener listener) {
		listeners.add(listener);
	}
	
	private void notifyListeners(String data){
		for(DownloadListener listener : listeners) {
			listener.outputUpdated(data);
		}
	}
	
	/**
	 * Checks the current operating system and returns the path of the yt-dl
	 * executable - the executable should automatically be installed to this
	 * path during the installation process. Linux Systems (not MAC) may be
	 * configured manually - if you are having issues, check the log and make
	 * sure the youtube-dl executable is in the expected directory
	 * 
	 * @return Path to the Youtube-DL executable on the local operating system
	 */
	private String getDownloader() {
		String OS = System.getProperty("os.name").toUpperCase();
		String downloader;
		if (OS.contains("WIN")) {
			downloader = System.getenv("APPDATA") + "\\YouTube Downloader 5.0\\execs\\youtube-dl.exe";
		} else if (OS.contains("MAC")) {
			downloader = System.getProperty("user.home") + "/Library/Application " + "Support";
		} else if (OS.contains("NUX")) {
			downloader = System.getProperty("user.home");
		} else {
			downloader = UNKNOWN;
		}
		return downloader;
	}
}
