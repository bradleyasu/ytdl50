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

import com.hexotic.utils.Settings;
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

	/**
	 * Get a thumbnail image of a video url with the --get-thumbnail 
	 * option for youtube-dl
	 * 
	 * @param url	
	 * 				URL to get the thumbnail for
	 * 
	 * @return
	 * 				Thumbnail for the video
	 * @throws IOException
	 */
	public Image getThumbnailUrl(String url) throws IOException {
		String[] args = {"--get-thumbnail", url };
		String thumbnailUrl = execute(args).trim();
		Log.getInstance().debug(this,"Loaded Image Thumnail: "+thumbnailUrl);
		
		ImageIcon icon = new ImageIcon(new URL(thumbnailUrl));
		return icon.getImage();
	}
	
	/**
	 * Uses --get-title and a specified url to get the title of 
	 * a video 
	 * 
	 * @param url 
	 * 				URL to get the title of
	 * @return
	 * 				Title of the video at the url
	 * @throws IOException
	 */
	public String getTitle(String url) throws IOException {
		String[] args = {"--get-title", url };
		String videoTitle = execute(args).trim();
		Log.getInstance().debug(this,"Loaded Video Title: "+videoTitle);
		return videoTitle;
	}

	/**
	 * Download a video with the options provided.  This method will execute
	 * the youtube-dl download functionality and evaluate user settings as paramter
	 * input for youtube-dl
	 * 
	 * @param url
	 * 				URL to download
	 * @param audio
	 * 				Audio format if true, video if false
	 * @param downloadDirectory
	 * 				The Location to download the video/audio (Desktop used by default)
	 * @throws IOException
	 */
	public void download(String url, boolean audio, String downloadDirectory, boolean useProxy) throws IOException {
		
		// Create a chace for the arguments (not sure how many there will be yet)
		List<String> argCache = new ArrayList<String>();

		// If audio, add youtube-dl arguments for converting to audio format
		if(audio){
			argCache.add("-x");
			argCache.add("--audio-format");
			argCache.add("mp3");
		}
		
		// If proxy is enabled, generate proxy ip address from configurations and add youtube-dl arguments
		if(useProxy){
			boolean isHttps = Settings.getInstance().getProperty("proxyIsHttps", "false").equals("true");
			String ip = Settings.getInstance().getProperty("proxyIP", "");
			String port = Settings.getInstance().getProperty("proxyPort", "8080");
		
			argCache.add("--proxy");
			
			// Generate the proxy url
			if(isHttps){
				argCache.add("https://"+ip+":"+port);
			} else {
				argCache.add("http://"+ip+":"+port);
			}
			
		}
		
		argCache.add("-o");
		argCache.add("\""+downloadDirectory+"\\%(title)s.%(ext)s\"");
		argCache.add(url);
		
		
		// Translate all of the parameters into an array
		String[] param = new String[argCache.size()];
		for(int i = 0 ; i < argCache.size(); i++){
			param[i] = argCache.get(i);
		}
		
		// Execute yt-dl with parameters
		execute(param);
	}
	
	/**
	 * Lists all supported extractors and returns in a 
	 * string array
	 */
	public String[] getSupported(){
		String[] arr = {"--list-extractors"};
		String[] extractors = {""};
		try {
			extractors = execute(arr).split("\n");
		} catch (IOException e) {
			Log.getInstance().error(this, "Failed to list extractors", e);
		}
		return extractors;
	}

	/**
	 * Uses the youtube-dl -U parameter to update the youtube-dl module
	 */
	public void update() {
		String[] arr = {"-U"};
		try {
			execute(arr);
		} catch (IOException e) {
			Log.getInstance().error(this, "Failed to update youtube-dl backend component", e);
		}
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

	/**
	 * Download listeners allow other objects to listen for output changes
	 * on the youtube-dl command line output
	 * 
	 * @param listener  
	 * 				Listener object to add
	 */
	public void addDownloadListener(DownloadListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * If youtube-dl output changes, notify any listeners attached
	 * 
	 * @param data
	 * 			Data the listeners need to be notified of - youtube-dl CLI output string
	 */
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
