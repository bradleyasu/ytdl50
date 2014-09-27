package com.hexotic.v2.gui.primary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.v2.gui.primary.downloaditem.Item;
import com.hexotic.v2.gui.theme.Theme;

public class DownloadContainer extends JPanel{

	private ExecutorService es = Executors.newFixedThreadPool(10);
	public DownloadContainer() {
		this.setBackground(Theme.MAIN_BACKGROUND);
		this.setLayout(new AnimatedGridLayout());

		
		//new Thread(item).start();
	}
	
	public void addDownload(String url){
		Item item = new Item(url);
		this.add(item);
		es.execute(item);
	}
	
}
