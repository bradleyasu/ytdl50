package com.hexotic.v2.downloader.popup;

public class PopupFactory {

	private static PopupWindow popup = null;

	public static PopupWindow getPopupWindow() {
		if (popup == null) {
			popup = new PopupWindow();
		}
		return popup;
	}

}
