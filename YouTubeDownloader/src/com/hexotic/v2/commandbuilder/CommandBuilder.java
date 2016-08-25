package com.hexotic.v2.commandbuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.hexotic.utils.Settings;
import com.hexotic.v2.console.Log;

/**
 * CommandBuilder
 * 
 * Stores the command line arguments to pass to youtube-dl.  These arguments will be saved
 * to the persistence file.  If nothing has been previously saved, the default arguments will be 
 * used
 * 
 * @author Bradley
 *
 */
public class CommandBuilder {
	
	private File builderCache;
	private static CommandBuilder instance;
	private Map<String, DomainCommands> commands;
	private DomainCommands defaultCommands;
	
	private CommandBuilder() {
		builderCache = new File(Settings.getInstance().getSettingsDirectory()+"\\bldrCache.bsr"); 
		commands = new HashMap<String, DomainCommands>();
		setupDefaultCommands();
		setupDefaultYoutubeCommands();
		loadCommands();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	           saveCommands();
	        }
	    }, "Command-Cache-Thread"));
	}
	
	@SuppressWarnings("unchecked")
	private void loadCommands(){
		if(builderCache.exists()){
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(builderCache);
				ois = new ObjectInputStream(fis);
				commands = (Map<String, DomainCommands>) ois.readObject();
				Log.getInstance().debug(this, "Commands cache successfully loaded for: "+commands.size()+" domains");
			} catch (IOException e) {
				Log.getInstance().error(this, "Command Cache found, but failed to read to read.  May be corrupt!!", e);
			} catch (ClassNotFoundException e) {
				Log.getInstance().error(this, "Command Cache found, but failed to read to read.  May be corrupt!!", e);
			}
		} else{
			Log.getInstance().warn(this, "No command cache found.  Using default configurations");
		}
	}
	
	private void saveCommands(){
		FileOutputStream out = null;
		ObjectOutputStream objStream = null;
		try {
			out = new FileOutputStream(builderCache);
			objStream = new ObjectOutputStream(out);
			objStream.writeObject(commands);
			Log.getInstance().debug(this, "Command cache saved for: "+commands.size()+" domains");
		} catch (IOException e) {
			Log.getInstance().error(this, "Failed to save command list cache", e);
		} finally {
			try {
				if(objStream != null){
					objStream.close();
				}
				if(out != null){
					out.close();
				}
			} catch (IOException e) { }
		}
	}
	
	
	private void setupDefaultYoutubeCommands(){
		CommandList audio = new AudioCommands();
		CommandList video = new VideoCommands();
	
		audio.addCommand("-x").addCommand("--audio-format").addCommand("mp3").addCommand("--no-check-certificate");
		video.addCommand("-f").addCommand("bestvideo+bestaudio").addCommand("--no-playlist").addCommand("--recode-video").addCommand("mp4").addCommand("--no-check-certificate");
		
		DomainCommands ytCommands = new DomainCommands("youtube.com");
		ytCommands.setAudioCommands(audio);
		ytCommands.setVideoCommands(video);
		addDomain("youtube.com", ytCommands);
		addDomain("youtu.be", ytCommands);
	}

	public void addDomain(String domain, DomainCommands commands){
		domain = domain.toUpperCase();
		this.commands.put(domain, commands);
	}
	
	public DomainCommands getCommands(String domainUrl) {
		domainUrl = getDomain(domainUrl);
		if(commands.containsKey(domainUrl)){
			return commands.get(domainUrl);
		}
		return defaultCommands;
	}
	
	private String getDomain(String url){
		String domain = url;
		URI uri;
		try {
			uri = new URI(url);
			domain = uri.getHost();
			domain = domain.startsWith("www.") ? domain.substring(4) : domain;
		} catch (URISyntaxException e) {
			Log.getInstance().error(this, "Couldn't find domain from url", e);
		}
		
		return domain.toUpperCase();
	}
	
	private void setupDefaultCommands(){
		CommandList audio = new AudioCommands();
		CommandList video = new VideoCommands();
	
		audio.addCommand("-x").addCommand("--audio-format").addCommand("mp3").addCommand("--no-check-certificate");
		video.addCommand("--recode-video").addCommand("mp4").addCommand("--no-check-certificate");
		
		defaultCommands = new DomainCommands("default");
		defaultCommands.setAudioCommands(audio);
		defaultCommands.setVideoCommands(video);
	}
	
	public static CommandBuilder builder(){
		if(instance == null){
			instance = new CommandBuilder();
		}
		
		return instance;
	}
	
}
