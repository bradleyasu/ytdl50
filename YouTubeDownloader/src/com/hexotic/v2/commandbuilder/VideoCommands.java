package com.hexotic.v2.commandbuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VideoCommands implements CommandList, Serializable{

	private static final long serialVersionUID = -3894201263942888380L;
	private List<String> commands;
	
	public VideoCommands(){
		commands = new ArrayList<String>();
	}
	
	@Override
	public void loadCommands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getCommands() {
		// TODO Auto-generated method stub
		return commands;
	}

	@Override
	public CommandList addCommand(String s) {
		commands.add(s);
		return this;
	}

	@Override
	public void removeCommand(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearCommands() {
		commands.clear();
	}

}
