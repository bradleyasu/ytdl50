package com.hexotic.v2.gui.primary;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Theme.MAIN_COLOR_FOUR);
		
		g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
		
	}
}
