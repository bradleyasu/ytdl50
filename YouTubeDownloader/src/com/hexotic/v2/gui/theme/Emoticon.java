package com.hexotic.v2.gui.theme;

import java.awt.Image;

import com.hexotic.lib.resource.Resources;

/**
 * This class serves as a resource for all classes to easily access 
 * emoticon icons
 * 
 * @author Bradley
 *
 */
public final class Emoticon {

	
	public static Image FACE_MATRIX = Resources.getInstance().getImage("faces/emotion_matrix.png");
	public static Image SUCCESS = Resources.getInstance().getImage("faces/tick.png");
	public static Image WARNING = Resources.getInstance().getImage("faces/warning.png");
	
	public static Image AUDIO_SMALL = Resources.getInstance().getImage("faces/music_sm.png");
	public static Image VIDEO_SMALL = Resources.getInstance().getImage("faces/film_sm.png");
	public static Image WAIT_SMALL = Resources.getInstance().getImage("faces/hourglass_sm.png");
	
	private Emoticon(){
		// Do nothing
	}
	
}
