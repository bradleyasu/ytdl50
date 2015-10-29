package com.hexotic.v2.gui.theme;

import java.awt.Image;

import com.hexotic.lib.resource.Resources;

/**
 * This class serves as a resource for all classes to easily access 
 * emoticon icons
 * 
 * @author Bradley Sheets
 *
 */
public final class Emoticon {

	
	public static Image FACE_MATRIX = null;
	public static Image SUCCESS = null;
	public static Image WARNING = null;
	
	public static Image AUDIO_SMALL = null;
	public static Image VIDEO_SMALL = null;
	public static Image WAIT_SMALL = null;
	
	
	static {
		// Try To Load Resources
	    try {
	      
	    	FACE_MATRIX = Resources.getInstance().getImage("faces/emotion_matrix.png");
	    	SUCCESS = Resources.getInstance().getImage("faces/tick.png");
	    	WARNING = Resources.getInstance().getImage("faces/warning.png");
	    	
	    	AUDIO_SMALL = Resources.getInstance().getImage("faces/music_sm.png");
	    	VIDEO_SMALL = Resources.getInstance().getImage("faces/film_sm.png");
	    	WAIT_SMALL = Resources.getInstance().getImage("faces/hourglass_sm.png");
	    } catch (Exception e) { } 
	  } 
	
	private Emoticon(){
		// Do nothing
	}
	
}
