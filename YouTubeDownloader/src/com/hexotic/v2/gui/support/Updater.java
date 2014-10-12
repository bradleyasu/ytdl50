package com.hexotic.v2.gui.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;

import com.hexotic.cons.Constants;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.gui.MainWindow;

public class Updater {
	private String updater;
	public Updater(){
	    String OS = System.getProperty("os.name").toUpperCase();
	    if (OS.contains("WIN")){
	        updater = System.getenv("APPDATA")+"\\YouTube Downloader 5.0\\execs\\uplink.exe";
	        updateCheck();
	    }else if (OS.contains("MAC")){
	        updater = System.getProperty("user.home") + "/Library/Application " + "Support";
	    }else if (OS.contains("NUX")){
	        updater = System.getProperty("user.home");
	    }else{
	    	updater = "UNKNOWN";
	    }
	}
	public void updateCheck(){
		if(!updater.equals("UNKNOWN")){
			try{
				Runtime rt = Runtime.getRuntime();
				//[process id] [program name] [program version] [update server directory] [update file name] [output program path]
				String pid = ManagementFactory.getRuntimeMXBean().getName();
				if(pid.contains("@")){
					pid = pid.split("@")[0];
				}
				
				String path = new File(MainWindow.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolutePath();
				path = path.substring(0, path.length()-1) + "YouTube Downloader 5.0.exe"; // WINDOWS SOLUTION ONLY
				// To udpate a version, place a file on the server called YT_5.0.0U
				// Next time, it will look for YT_5.0.1U and so on
				Log.getInstance().debug(this, "Software Process ID: "+pid);
				String[] cmd = { updater+"", pid, Constants.PROG_NAME, Constants.VERSION, Constants.UPDATE_SERVER, "YT_"+Constants.VERSION+"U", path};
				
				Process proc = rt.exec(cmd);
		        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		        String s;
		        while ((s = stdInput.readLine()) != null) {
		        	Log.getInstance().debug(this, s);
				}
			}catch(Exception e){e.printStackTrace();}
		}
	}
	
}
