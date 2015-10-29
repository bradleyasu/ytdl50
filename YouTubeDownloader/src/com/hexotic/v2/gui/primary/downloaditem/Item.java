package com.hexotic.v2.gui.primary.downloaditem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.hexotic.lib.exceptions.ResourceException;
import com.hexotic.lib.resource.Resources;
import com.hexotic.utils.Settings;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.downloader.DownloadListener;
import com.hexotic.v2.downloader.Downloader;
import com.hexotic.v2.gui.theme.Emoticon;
import com.hexotic.v2.gui.theme.Theme;

/**
 * An Item holds information about a requested download. It displays information
 * in a square panel related to the download name, url, thumbnail, and download
 * percentage.
 * 
 * There are also options to cancel, restart, and modify ID3 tags from this
 * panel
 * 
 * @author Bradley Sheets
 * 
 */
public class Item extends JPanel implements Runnable, Comparable<Item> {

	/* Generated Serial Version ID */
	private static final long serialVersionUID = -7562231924818463232L;

	/* URL submitted from user for download */
	private String url;

	/* The youtube-dl attached for this download */
	private Downloader downloader;

	/* By default, 0 percent of the download is complete */
	private ProgressCircle progress;

	/* Item title text */
	private String title = "Unknown Title";

	/* Use default thumbnail to start */
	private Image thumbnail = null;

	/* by default, the item isn't downloaded yet */
	private boolean downloaded = false;

	private boolean failed = false;
	private int id = 0;

	private boolean useProxy = false;

	private boolean isAudio = false;

	private boolean downloadStarted = false;

	
	public Item(String url, int id) {
		this.id = id;
		this.setPreferredSize(new Dimension(Theme.DOWNLOAD_ITEM_WIDTH, Theme.DOWNLOAD_ITEM_HEIGHT));
		this.setBackground(Theme.DOWNLOAD_ITEM_BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(Theme.DOWNLOAD_ITEM_BORDER));
		this.url = url;
		try {
			thumbnail = Resources.getInstance().getImage("item_default.png");
		} catch (ResourceException e) {	}
		
		downloader = new Downloader();

		// Create a new progress for this item
		progress = new ProgressCircle();

		// Colorize the progress circle with the YT downloader theme
		progress.setColor(Theme.MAIN_COLOR_TWO, Theme.MAIN_COLOR_THREE);

		// Begin the "cycle" animation
		progress.cycle();

		


	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw video thumbnail image
		g2d.drawImage(thumbnail, 0, 0, getWidth(), getHeight() - 40, null);

		// draw dark overlay
		if (progress.getProgress() < 100) {
			g2d.setPaint(new Color(0, 0, 0, 145));
			g2d.fillRect(0, 0, getWidth(), getHeight() - 40);
			int size = getWidth() / 2;
			progress.Draw(g, getWidth() / 2 - size / 2, 10, size, size);
		}

		// If proxy, draw somekind of indicator (in this case, a matrix face)
		if (downloaded && !failed) {
			g2d.drawImage(Emoticon.SUCCESS, 5, 5, null);
		} else if (failed) {
			g2d.drawImage(Emoticon.WARNING, 5, 5, null);
		} else if (useProxy) {
			g2d.drawImage(Emoticon.FACE_MATRIX, 5, 5, null);
		}

		// Draw thumbnail splitter
		g2d.setColor(Theme.DOWNLOAD_ITEM_BORDER);
		g2d.drawLine(0, getHeight() - 40, getWidth(), getHeight() - 40);

		// Draw Polygon
		g2d.setColor(Theme.DOWNLOAD_ITEM_BACKGROUND);
		int[] ypoints = { getHeight() - 40, getHeight() - 50, getHeight() - 40 };
		int[] xpoints = { getWidth() - 30, getWidth() - 20, getWidth() - 10 };
		g2d.fillPolygon(xpoints, ypoints, 3);

		// Draw Polygon border
		g2d.setColor(Theme.DOWNLOAD_ITEM_BORDER);
		g2d.drawPolygon(xpoints, ypoints, 3);
		g2d.setColor(Theme.DOWNLOAD_ITEM_BACKGROUND);
		g2d.drawLine(xpoints[0] + 1, ypoints[0], xpoints[2] - 1, ypoints[2]);

		// Draw the title of the video
		g2d.setFont(new Font("Arial", Font.BOLD, 12));
		g2d.setColor(Color.BLACK);
		if (useProxy) {
			g2d.drawString("[ Proxy Download ]", 20, getHeight() - 24);
		} else {
			g2d.drawString(title, 20, getHeight() - 24);
		}

		if (!downloadStarted) {
			g2d.drawImage(Emoticon.WAIT_SMALL, 2, getHeight() - 37, null);
		} else if (isAudio) {
			g2d.drawImage(Emoticon.AUDIO_SMALL, 2, getHeight() - 37, null);
		} else {
			g2d.drawImage(Emoticon.VIDEO_SMALL, 2, getHeight() - 37, null);
		}

		// Draw the url of the video
		g2d.setColor(new Color(0x1874CD));
		g2d.setFont(new Font("Arial", Font.PLAIN, 9));
		g2d.drawString(url, 5, getHeight() - 11);
		
	}

	private void updateImage() {
		try {
			thumbnail = downloader.getThumbnailUrl(url);
		} catch (IOException e) {
			Log.getInstance().error(this, "Couldn't load thumbnail image for url: " + url, e);
		}
	}

	private void updateTitle() {
		try {
			title = downloader.getTitle(url);
		} catch (IOException e) {
			Log.getInstance().error(this, "Couldn't load title for url: " + url, e);
		}
	}

	private void startDownload() {
		downloader.addDownloadListener(new DownloadListener() {
			@Override
			public void outputUpdated(String output) {
				if (output.contains("%")) {
					String[] data = output.split("\\s+");
					String percent = data[1];
					String[] percentData = percent.split("\\.");

					String status = percentData[0].replaceAll("[^0-9]", "");
					// If there was an error parsing the
					if ("".equals(status)) {
						status = "99";
					}
					progress.setProgress(Double.parseDouble(status));
				}
			}
		});
		Log.getInstance().debug(this, "Download Started: " + url);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// Set flag to indicate downloading has begun
					downloadStarted = true;

					// get the configured download directory, use desktop by
					// default
					File desktop = new File(System.getProperty("user.home"), "Desktop");
					String downloadDir = Settings.getInstance().getProperty("downloadDir", desktop.getAbsolutePath());

					// should this download use a proxy?
					useProxy = Settings.getInstance().getProperty("useProxy", "false").equals("true");
					isAudio = Boolean.valueOf(Settings.getInstance().getProperty("audioFormat", "false"));

					downloader.download(url, isAudio, downloadDir, useProxy);

					Log.getInstance().debug(this, "Download processing finished");
					if (progress.getProgress() != 100) {
						progress.setProgress(100);
						failed = true;
					}
					downloaded = true;
				} catch (IOException e) {
					Log.getInstance().error(this, "Couldn't download video: " + url, e);
					failed = true;
				}
			}
		}).start();
		;
	}

	public int getId() {
		return id;
	}

	/**
	 * Download the item submitted by the user and update the download item UI
	 * as the download progresses
	 */
	@Override
	public void run() {
		updateImage();
		updateTitle();

		// While the downloader is downloading, add an event to update the
		// percentage/status
		startDownload();
		while (!downloaded && !failed) {
			this.revalidate();
			this.repaint();
			try {
				Thread.sleep(75);
			} catch (InterruptedException e) {
				Log.getInstance().error(this, "Animation Error", e);
			}
		}
		progress.stopCycle();
		// just one final time to make sure everything is up to date
		this.revalidate();
		this.repaint();
		if ("true".equals(Settings.getInstance().getProperty("removeOnComplete", "false"))) {
			this.setVisible(false);
		}
	}

	@Override
	public int compareTo(Item item) {
		return getId() - item.getId();
	}

}
