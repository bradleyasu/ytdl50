package com.hexotic.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.hexotic.lib.ui.notificationbar.NotificationBar;
import com.hexotic.lib.ui.notificationbar.NotificationCenter;

public class TopPanel extends JPanel{
	
	public TopPanel(){
		this.setLayout(new BorderLayout());
		
		JPanel menuNotifyPanel = new JPanel(new BorderLayout());
		menuNotifyPanel.add(new YTMenu(), BorderLayout.NORTH);

		NotificationBar notify = new NotificationBar();
        menuNotifyPanel.add(notify, BorderLayout.CENTER);
		NotificationCenter.getInstance().registerNotificationBar("primary", notify);
		this.add(menuNotifyPanel, BorderLayout.NORTH);
		this.add(new UrlPanel(), BorderLayout.CENTER);
		
	}
}

