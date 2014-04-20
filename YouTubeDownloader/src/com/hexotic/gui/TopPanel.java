package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.hexotic.cons.Constants;
import com.hexotic.lib.ui.notificationbar.Notification;
import com.hexotic.lib.ui.notificationbar.NotificationBar;

public class TopPanel extends JPanel{
	
	public TopPanel(){
		this.setLayout(new BorderLayout());
		
		JPanel menuNotifyPanel = new JPanel(new BorderLayout());
		menuNotifyPanel.add(new YTMenu(), BorderLayout.NORTH);

		NotificationBar notify = new NotificationBar();
        menuNotifyPanel.add(notify, BorderLayout.CENTER);
		
		this.add(menuNotifyPanel, BorderLayout.NORTH);
		this.add(new UrlPanel(), BorderLayout.CENTER);
		
		
		notify.showNotification(new Notification(Notification.ACCEPT, Notification.YES_NO, "Your version of the YouTube Downloader ("+Constants.VERSION+") is now up to date! Would you like to know what all was updated?"));
	}
}

