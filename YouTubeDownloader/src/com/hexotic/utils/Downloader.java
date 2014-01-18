package com.hexotic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.v1.ID3V1Tag.Genre;

import com.hexotic.cons.Constants;
import com.hexotic.gui.DownloadItem;
import com.hexotic.gui.MessageBox;

/**
 * This class is responsible for downloading media.  It will execute the 
 * youtube-dl open source software
 * 
 * @author Bradley Sheets
 *
 */
public class Downloader {

	private String downloader = null;
	private DownloadItem download;
	private Process proc;
	private String title = null;
	private String url = null;
	private String destination = null;
	private Tagger tagger;
	private int downloadStatus;
	
	public Downloader(DownloadItem d){
		download = d;
		tagger = new Tagger();
		downloader = CentralDownloadControl.getInstance().getDownloader();
		downloadStatus = Constants.DOWNLOAD_READY;
	}
	
	public String getThumbnailUrl() throws IOException{
		if(this.url == null){
			String url = "UNKNOWN";
			String s;
			Runtime rt = Runtime.getRuntime();
			String[] cmd = { downloader+"", "--get-thumbnail", download.getUrl()};
			proc = rt.exec(cmd);
	        BufferedReader stdInput = new BufferedReader(new 
	        InputStreamReader(proc.getInputStream()));
	
			
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			    url = s.trim();
			}
			this.url = url;
			return url;
		}
		return this.url;
	}
	
	public DownloadItem getDownloadItem(){
		return download;
	}
	public String getTitle() throws IOException{
		if(this.title == null){
			String title = "Unknown";
			String s;
			Runtime rt = Runtime.getRuntime();
			String[] cmd = { downloader+"", "--get-title", download.getUrl()};
			
			proc = rt.exec(cmd);
	        BufferedReader stdInput = new BufferedReader(new 
	        InputStreamReader(proc.getInputStream()));
	
			
			while ((s = stdInput.readLine()) != null) {
			    System.out.println(s);
			    title = s.trim();
			}
			this.title = title;
			return title;
		}
		return this.title;
	}
	
	public void download(boolean asMP3) throws IOException, NumberFormatException, ID3Exception{
		downloadStatus = Constants.DOWNLOAD_INPROGRESS;
		this.download.setState(Constants.INPROGRESS);
		String s;
		Runtime rt = Runtime.getRuntime();
		
		String saveTo = CentralDownloadControl.getInstance().getDownloadDirectory();
		
		String[] cmd = { downloader+"", "-x", "--audio-format", "mp3", "-o", "\""+saveTo+"\\%(title)s.%(ext)s\"", download.getUrl()};
		if(!asMP3){
			cmd[1] = "";
			cmd[2] = "";
			cmd[3] = "";
			cmd[5] = "\""+saveTo+"\\%(title)s.%(ext)s\"";
		}
		proc = rt.exec(cmd);
		for(String str: cmd)
			System.out.print(str+" ");
		System.out.println();
        BufferedReader stdInput = new BufferedReader(new 
        InputStreamReader(proc.getInputStream()));
		while ((s = stdInput.readLine()) != null) {
		    if(s.contains("%")){
			    String[] data = s.split("\\s+");
			    String percent = data[1];
			    String[] percentData = percent.split("\\.");
			    download.setStatus(Integer.parseInt(percentData[0].replaceAll("[^0-9]", "")));
		    }
		    if(s.contains("[download] Destination: ")){
		    	destination = s.replace("[download] Destination: ", "");
		    }
		}
		if(downloadStatus == Constants.DOWNLOAD_ABORTED){
			this.download.setState(Constants.CANCELED);
		}else{
			if(asMP3){
				if(destination != null){
					try{
						tagger.applyID3(destination, getTitle());
					}catch(Exception e){ 
						new MessageBox(Constants.MSG_5, "OhNos!", "I had a problem applying your ID3 Tags", e.toString());
					}
				}else{
					new MessageBox(Constants.MSG_5, "OhNos!", "I had a problem applying your ID3 Tags", "Unresolved Destination");
				}
			}
			downloadStatus = Constants.DOWNLOAD_COMPLETE;
			this.download.setState(Constants.COMPLETE);
		}
	}
	
	public String getDestination(){
		return destination;
	}
	
	public void applyTags(String id3Album, String id3Artist, String id3Comment, String id3Title, String id3Year, Genre id3Genre){
		tagger.setId3Album(id3Album);
		tagger.setId3Artist(id3Artist);
		tagger.setId3Comment(id3Comment);
		tagger.setId3Genre(id3Genre);
		tagger.setId3Title(id3Title);
		tagger.setId3Year(id3Year);
		if(downloadStatus == Constants.DOWNLOAD_COMPLETE){
			if(destination != null){
				try {
					tagger.applyID3(destination, getTitle());
				} catch (Exception e) {
					new MessageBox(Constants.MSG_5, "OhNos!", "I had a problem applying your ID3 Tags", e.toString());
				}
			}else{
				new MessageBox(Constants.MSG_5, "OhNos!", "I had a problem applying your ID3 Tags", "Unresolved Destination");
			}
		}
	}
	
	public Tagger getTagger(){
		return tagger;
	}
	
	
	public void cancel(){
		downloadStatus = Constants.DOWNLOAD_ABORTED;
		proc.destroy();
	}
}
