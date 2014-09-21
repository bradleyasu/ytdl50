package com.hexotic.v2.gui.primary.downloaditem;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;

import com.hexotic.lib.resource.Resources;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.gui.primary.Drawable;
import com.hexotic.v2.gui.theme.Theme;

/**
 * Pretty progress circle thingy. This object is a modern looking progress bar
 * in the shape of a circle
 * 
 * @author Bradley Sheets
 * 
 */
public class ProgressCircle implements Drawable {

	/* By Default, the progress is 0% */
	private double progress = 100;

	private Color colorOne = Color.BLACK;
	private Color colorTwo = Color.GRAY;

	private Color fontColor = Color.white;
	private Font font = new Font("Arial", Font.BOLD, 28);

	public ProgressCircle() {

		try {
			font = Resources.getInstance().getFont("default.ttf");
			font = font.deriveFont(28.0f);
			Log.getInstance().debug(this, "Fonts Successfully Loaded");
		} catch (FontFormatException e) {
			Log.getInstance().error(this, "Couldn't load font in progress elements", e);
		} catch (IOException e) {
			Log.getInstance().error(this, "Couldn't load font in progress elements", e);
		}
		
	}

	public void setProgress(double progress) {
		if (progress > 100) {
			progress = 100;
		}
		if (progress < 0) {
			progress = 0;
		}
		
		this.progress = progress;
	}

	public void setColor(Color start, Color end) {
		colorOne = start;
		colorTwo = end;
	}

	@Override
	public void Draw(Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		g2.setStroke(new BasicStroke(8.0f));
		GradientPaint gp = new GradientPaint(75, 75, colorOne, 10, 95, colorTwo, true);
		g2.setPaint(gp);

		int arc = (int) (progress / 100.00 * 360);
		g2.drawArc(x, y, width, height, 90, arc * -1);

		// Draw and Center percent String
		g2.setFont(font);
		g2.setColor(fontColor);
		String percent = String.valueOf((int) progress) + "%";
		FontMetrics metrics = g2.getFontMetrics(font);
		int pX = (Theme.DOWNLOAD_ITEM_WIDTH - metrics.stringWidth(percent)) / 2;
		int pY = (metrics.getAscent() + (Theme.DOWNLOAD_ITEM_HEIGHT - (metrics.getAscent() + metrics.getDescent() + metrics.getHeight())) / 2) - 5;
		g2.drawString(percent, pX, pY);
	}
}
