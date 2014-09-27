package com.hexotic.v2.gui.primary;

import javax.swing.JPanel;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.gui.primary.downloaditem.Item;
import com.hexotic.v2.gui.theme.Theme;

public class DownloadContainer extends JPanel{

	public DownloadContainer() {
		this.setBackground(Theme.MAIN_BACKGROUND);
		this.setLayout(new AnimatedGridLayout());

		Item item = new Item("http://www.youtube.com/watch?v=fS4XjXAIvsA");
		this.add(item);
		
		new Thread(item).start();
	}
	
}
