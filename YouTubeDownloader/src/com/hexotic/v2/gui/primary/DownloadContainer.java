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

		for (int i = 0 ; i < 32; i++ ) {
			Item item = new Item();
			this.add(item);
			
			new Thread(item).start();
			Log.getInstance().consolePrint("Loaded Item: "+i);
		}
	}
	
}
