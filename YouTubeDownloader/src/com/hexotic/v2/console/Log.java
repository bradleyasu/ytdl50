package com.hexotic.v2.console;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * It's pretty simple.  This is a singleton class for writing software logs
 * 
 * The reason I do it this way is so I have a centralized location of log formats.
 * I can change the output format on the fly
 * 
 * @author Bradley Sheets
 *
 */
public class Log {

	private static Log instance;
	private Logger logger = Logger.getLogger(Log.class);
	private Console console = null;
	
	private Log(){
		DOMConfigurator.configure("YTLog.xml");
	}
	
	public void setConsole(Console console){
		this.console = console;
	}
	
	public void debug(Object parent, String message){
		logger.debug(parent.getClass().getSimpleName()+" [ " + message +" ]");
		consolePrint(message);
	}
	
	public void warn(Object parent, String message) {
		logger.warn(parent.getClass().getSimpleName()+" [ " + message +" ]");
		consolePrint(message);
	}
	
	public void error(Object parent, String message){
		error(parent, message, new Exception("UNSPECIFIED EXCEPTION"));
	}
	
	public void error(Object parent, String message, Exception ex){
		logger.fatal(parent.getClass().getSimpleName()+" [ " + message +" ]", ex);
		consolePrint(message);
	}
	
	public void consolePrint(String s) {
		if(console != null) {
			console.println(s);
		}
	}
	
	public static Log getInstance(){
		if (instance ==  null){
			instance = new Log();
		}
		return instance;
	}
}
