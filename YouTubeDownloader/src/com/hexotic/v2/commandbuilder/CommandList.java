package com.hexotic.v2.commandbuilder;

import java.io.Serializable;
import java.util.List;

public interface CommandList extends Serializable{

	public CommandList addCommand(String s);
	public void removeCommand(String s);
	public void clearCommands();
	
	public void loadCommands();
	public List<String> getCommands();
}
