package com.hexotic.v2.commandbuilder;

import java.io.Serializable;

public class DomainCommands implements Serializable{

	private static final long serialVersionUID = 4147804025993874104L;
	private String domain;
	private CommandList audioCommands;
	private CommandList videoCommands;
	
	public DomainCommands(String domain){
		this.domain = domain;
	}
	
	public String getDomain(){
		return domain;
	}
	
	public void setAudioCommands(CommandList audio){
		audioCommands = audio;
	}
	
	public void setVideoCommands(CommandList video){
		videoCommands = video;
	}
	
	public CommandList getAudioCommands(){
		return audioCommands;
	}
	
	public CommandList getVideoCommands() {
		return videoCommands;
	}
	
	
}
