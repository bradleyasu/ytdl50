package com.hexotic.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.validator.routines.UrlValidator;

import com.hexotic.cons.Constants;
import com.hexotic.gui.DownloadItem;
import com.hexotic.gui.MessageBox;
/**
 * 
 * CentralDownloadControl is responsible for verifying URL's and
 * downloading managing each item for download to local file system
 * 
 * @author Bradley Sheets
 *
 */
public class CentralDownloadControl extends Observable{
	private ArrayList<DownloadItem> downloads = new ArrayList<DownloadItem>();
	private static CentralDownloadControl instance = null;
	private boolean downloadOnCopy = false;
	private boolean downloadAsMp3 = false;
	private boolean useProxy = false;
	private String downloader = null;
	private String downloadDirectory = "";
	private int id = 0;
	
	String[] validUrls = { "youtube.com/watch?v=",
						"bandcamp.com",
						"8tracks.com",
						"break.com",
						"gamespot.com",
						"redtube.com",
						"instagram.com",
						"wimp.com",
						"vine.com",
						"tube8.com",
						"vevo.com",
						"comedycentral.com",
						"metacafe.com",
						"liveleak.com",
						"dailymotion.com",
						"photobucket.com",
						"video.yahoo.com",
						"facebook.com",
						"blip.tv",
						"collegehumor.com",
						"xvideos.com",
						"soundcloud.com",
						"mixcloud.com",
						"xhamster.com",
						"myspass.com",
						"myspass.de",
						"tumblr.com",
						"mtv.com",
						"youku.com",
						"xnxx.com", 
						"youjizz.com",
						"pornotube.com",
						"youporn.com",
						"pornhub.com",
						"spankwire.com",
						"plus.google.com",
						"arte.tv",
						"nba.com",
						"justin.tv",
						"funnyordie.com",
						"steam.com",
						"ustream.com",
						"rbmaradio.com",
						"nick.com",
						"colbertnation.com",
						"thedailyshow.com",
						"vimeo.com",
						"vk.com",
						"malemotion.com",
						"extremetube.com",
						"southparkstudios.com",
						"ted.com",
						"xtube.com",
						"videoweed.es",
						"nowvideo.sx",
						"divxstage.eu",
						"movshare.net",
						"nbc.com",
						"veoh.com",
						"video.google.com"};
	
	private CentralDownloadControl(){
		downloadDirectory = Settings.getInstance().getProperty("downloadDir");
		if(downloadDirectory == null || downloadDirectory.equals("")){
			FileSystemView filesys = FileSystemView.getFileSystemView();
			downloadDirectory = filesys.getHomeDirectory().toString();
			System.out.println("Couldn't find saved download directory location.  Using desktop instead");
		}
	}
	
	public String getDownloader(){
		if(downloader == null){
			setDownloader();
		}
		File f = new File(downloader);
		if(downloader == "UNKNOWN" || !f.exists()){
			new MessageBox(Constants.MSG_2, "Core Component Error", "You may have to reinstall the software to fix this.",
							"A core component of the downloader is missing or cannot be found due to incompatible operating system.");
		}
		return downloader;
	}
	
	public String getDownloadDirectory(){
		return downloadDirectory;
	}
	
	public void setDownloadDirectory(String directory){
		downloadDirectory = directory;
		Settings.getInstance().saveProperty("downloadDir", directory);
	}
	
	public String updateDownloader() throws IOException{
		String s;
		Process proc;
		Runtime rt = Runtime.getRuntime();
		
		String[] cmd = { getDownloader()+"", "--update"};
		proc = rt.exec(cmd);
        BufferedReader stdInput = new BufferedReader(new 
        InputStreamReader(proc.getInputStream()));
		String output = "";
		while ((s = stdInput.readLine()) != null) {
			output = output+s;
		}
		return output;
	}
	
	private void setDownloader(){
	    String OS = System.getProperty("os.name").toUpperCase();
	    if (OS.contains("WIN")){
	        downloader = System.getenv("APPDATA")+"\\YouTube Downloader 5.0\\execs\\youtube-dl.exe";
	    }else if (OS.contains("MAC")){
	        downloader = System.getProperty("user.home") + "/Library/Application " + "Support";
	    }else if (OS.contains("NUX")){
	        downloader = System.getProperty("user.home");
	    }else{
	    	downloader = "UNKNOWN";
	    }
	}
	
	public void addDownload(DownloadItem download){
		downloads.add(download);
		setChanged();
		notifyObservers(download);
	}
	
	public void addDownload(String url){
		DownloadItem download = new DownloadItem(url);
		downloads.add(download);
		setChanged();
		notifyObservers(download);
	}
	
	public DownloadItem getLast(){
		return downloads.get(downloads.size()-1);
	}
	
	public boolean isValid(String url){
		String[] schemes = {"http","https"};
		UrlValidator urlValidator = new UrlValidator(schemes);
		if((urlValidator.isValid(url) || url.contains("nowvideo.sx")) && checkUrl(url)){
			return true;
		}
		return false;
	}
	
	public void setDownloadAsMP3(boolean bool){
		downloadAsMp3 = bool;
	}
	
	public boolean downloadAsMP3(){
		return downloadAsMp3;
	}
	
	public void setUseProxy(boolean useProxy){
		this.useProxy = useProxy;
	}
	
	public boolean useProxy(){
		return useProxy;
	}
	
	private boolean checkUrl(String url){
		for(String x : validUrls){
			if(url.contains(x)){
				return true;
			}
		}
		return false;
	}
	
	public void setDownloadOnCopy(boolean doc){
		if(downloadOnCopy != doc){
			downloadOnCopy = doc;
			setChanged();
			notifyObservers("MENU");
		}
	}
	
	public boolean downloadOnCopy(){
		return downloadOnCopy;
	}
	
	public static CentralDownloadControl getInstance(){
		if(instance == null){
			instance = new CentralDownloadControl();
		}
		return instance;
	}
	
	public int nextId(){
		return id++;
	}
	
}
