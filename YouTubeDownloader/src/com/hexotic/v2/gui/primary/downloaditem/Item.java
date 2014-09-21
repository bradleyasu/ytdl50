package com.hexotic.v2.gui.primary.downloaditem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.hexotic.lib.resource.Resources;
import com.hexotic.v2.gui.theme.Theme;

/**
 * An Item holds information about a requested download.  It displays information in a 
 * square panel related to the download name, url, thumbnail, and download percentage.
 * 
 * There are also options to cancel, restart, and modify ID3 tags from this panel
 * 
 * @author Bradley Sheets
 *
 */
public class Item extends JPanel implements Runnable{

	/* Generated Serial Version ID */
	private static final long serialVersionUID = -7562231924818463232L;
	
	/* URL submitted from user for download */
	private String url = "http://www.youtube.com/watch?v=ZauC0ZTqggI";
	
	/* By default, 0 percent of the download is complete */
	private ProgressCircle progress;
	
	/* Item title text */
	private String title = "Unknown Title";
	
	/* Use default thumbnail to start */
	private Image thumbnail = Resources.getInstance().getImage("item_default.png"); 
	
	public Item() {
		this.setPreferredSize(new Dimension(Theme.DOWNLOAD_ITEM_WIDTH, Theme.DOWNLOAD_ITEM_HEIGHT));
		this.setBackground(Theme.DOWNLOAD_ITEM_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(Theme.DOWNLOAD_ITEM_BORDER));
		
		progress = new ProgressCircle();
		progress.setColor(Theme.MAIN_COLOR_TWO, Theme.MAIN_COLOR_THREE);
		progress.cycle();
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Graphics2D g2d = (Graphics2D) g;
		 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		 // Draw video thumbnail image
		 g2d.drawImage(thumbnail, 0,0, getWidth(), getHeight()-40, null);

		 // draw dark overlay
		 if(progress.getProgress() < 100) {
			 g2d.setPaint(new Color(0,0,0,145));
			 g2d.fillRect(0, 0, getWidth(), getHeight()-40);
			 int size = getWidth()/2;
			 progress.Draw(g, getWidth()/2 - size/2, 10, size, size);
		 } 
		 
		 // Draw thumbnail splitter
		 g2d.setColor(Theme.DOWNLOAD_ITEM_BORDER);
		 g2d.drawLine(0, getHeight()-40, getWidth(), getHeight()-40);
		 
		 // Draw Polygon
		 g2d.setColor(Theme.DOWNLOAD_ITEM_BACKGROUND);
		 int[] ypoints = {getHeight()-40, getHeight()-50, getHeight() - 40};
		 int[] xpoints = {getWidth()-30, getWidth()-20, getWidth()-10};
		 g2d.fillPolygon(xpoints, ypoints, 3);
		 
		 // Draw Polygon border
		 g2d.setColor(Theme.DOWNLOAD_ITEM_BORDER);
		 g2d.drawPolygon(xpoints, ypoints, 3);
		 g2d.setColor(Theme.DOWNLOAD_ITEM_BACKGROUND);
		 g2d.drawLine(xpoints[0]+1, ypoints[0], xpoints[2]-1, ypoints[2]);
		 
		 g2d.setFont(new Font("Arial", Font.PLAIN, 15));
		 g2d.setColor(Color.BLACK);
		 g2d.drawString(title, 5, getHeight()-27);
		 
		 g2d.setColor(new Color(0x1874CD));
		 g2d.setFont(new Font("Arial", Font.PLAIN, 10));
		 g2d.drawString(url, 7, getHeight()-17);
		 
	 }

	@Override
	public void run() {
		
		Random rand = new Random();
		for(int i = 0; i <= 100; i++){
			progress.setProgress(i);
			this.revalidate();
			this.repaint();
			try {
				Thread.sleep(rand.nextInt(100) * 10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
