package com.hexotic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

	private static Settings instance = null;
	private String settingsDir = ".ytd50hd";
	private String settingsFile = "settings.ini";
	private Properties prop;
	private File settings;
	private Settings(){
		settings = new File(getSettingsDirectory()+"\\"+settingsFile);
		prop = new Properties();
		if(settings.exists()){
			try {
				prop.load(new FileInputStream(settings));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getProperty(String property){
		return prop.getProperty(property);
	}
	
	
	public String getProperty(String property, String def){
		return prop.getProperty(property, def);
	}
	
	public void saveProperty(String property, String value){
		prop.setProperty(property, value);
		try {
			prop.store(new FileOutputStream(settings), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Settings getInstance(){
		if (instance == null){
			instance = new Settings();
		}
		return instance;
	}
	
	
	
	public File getSettingsDirectory() {
	    String userHome = System.getProperty("user.home");
	    if(userHome == null) {
	        throw new IllegalStateException("user.home==null");
	    }
	    File home = new File(userHome);
	    File settingsDirectory = new File(home, settingsDir);
	    if(!settingsDirectory.exists()) {
	        if(!settingsDirectory.mkdir()) {
	            throw new IllegalStateException(settingsDirectory.toString());
	        }
	    }
	    return settingsDirectory;
	}
}
