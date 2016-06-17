package com.hexotic.cons;

import java.net.URL;

/**
 * This class contains constant variables that will be used throughout the
 * software.  
 * 
 * The version number is will be sent to updater software to decide if updates
 * are required. 
 * 
 * @author Bradley Sheets
 *
 */
public class Constants { 

	public static final String VERSION = "5.1.3";
	
	public static final String PROG_NAME = "Youtube Downloader";
	public static final String COMPANY_NAME = "Hexotic Software";
	
	public static final String UPDATE_SERVER = "http://hexotic.net/updates/ytdownloader";
	
	public static final String MSG_1 = "images/emotion_adore.png";
	public static final String MSG_2 = "images/emotion_beat_brick.png";
	public static final String MSG_3 = "images/emotion_dribble.png";
	public static final String MSG_4 = "images/emotion_hitler.png";
	public static final String MSG_5 = "images/emotion_sad.png";

	public static final String START = "start";
	public static final String READY = "ready";
	public static final String COMPLETE = "complete";
	public static final String INPROGRESS = "inprogress";
	public static final String CANCELED = "canceled";
	public static final String FAILED = "failed";
	
	public static final int DOWNLOAD_READY = -1;
	public static final int DOWNLOAD_INPROGRESS = 0;
	public static final int DOWNLOAD_COMPLETE = 1;
	public static final int DOWNLOAD_ABORTED = 2; 
	public static final int DOWNLOAD_FAILED = 3;


}
