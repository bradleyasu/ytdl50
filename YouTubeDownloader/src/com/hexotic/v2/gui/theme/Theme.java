package com.hexotic.v2.gui.theme;

import java.awt.Color;
import java.awt.Font;

public final class Theme {
 
	public static final Color MAIN_COLOR_ONE = new Color(0xff0066);
	public static final Color MAIN_COLOR_TWO = new Color(255,34,102);
	public static final Color MAIN_COLOR_THREE = new Color(255,85,51);
	public static final Color MAIN_COLOR_FOUR = new Color(255,68,85);
	public static final Color MAIN_COLOR_FIVE = new Color(255,0,153);
	
	// Top/Control bar colors 
	public static final Color CONTROL_BAR_BACKGROUND = new Color(0xfafafa);
	public static final Color CONTROL_BAR_BORDER = new Color(0xdadada);
	
	public static final Color TRANSPARENT = new Color(0,0,0,0);

	public static final Font CONTROL_BAR_FONT = new Font("Arial", Font.BOLD, 11);
	public static final Font SWITCH_FONT = new Font("Arial", Font.BOLD, 10);
	
	public static final Color MAIN_BACKGROUND = new Color(0xf1f1f1);
	
	public static final int DOWNLOAD_ITEM_HEIGHT = 155;
	public static final int DOWNLOAD_ITEM_WIDTH = 185;
	
	public static final Color DOWNLOAD_ITEM_BORDER = new Color(0xababab);
	public static final Color DOWNLOAD_ITEM_BORDER_HOVER = MAIN_COLOR_TWO;
	public static final Color DOWNLOAD_ITEM_BACKGROUND = new Color(0xffffff);
	
	
	public static final Color DARK = new Color(0x302013);
	public static final Color DARK_SHADOW = new Color(0x181009);
	
	private Theme() { /* Private class, whacha gon do */ }
}
