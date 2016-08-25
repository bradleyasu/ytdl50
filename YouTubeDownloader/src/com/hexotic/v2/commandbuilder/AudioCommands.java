package com.hexotic.v2.commandbuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AudioCommands implements CommandList, Serializable{

	private static final long serialVersionUID = 6945782845325222665L;
	private List<String> commands;
	
	public AudioCommands(){
		commands = new ArrayList<String>();
	}

	@Override
	public void loadCommands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getCommands() {
		return commands;
	}

	@Override
	public CommandList addCommand(String s) {
		commands.add(s);
		return this;
	}

	@Override
	public void removeCommand(String s) {
		
	}

	@Override
	public void clearCommands() {
		commands.clear();
	}
	
}
