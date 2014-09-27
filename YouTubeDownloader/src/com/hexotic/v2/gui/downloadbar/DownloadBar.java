package com.hexotic.v2.gui.downloadbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.hexotic.v2.gui.components.TextFieldWithPrompt;
import com.hexotic.v2.gui.theme.Theme;

public class DownloadBar extends JPanel {

	private TextFieldWithPrompt urlInput;

	public DownloadBar() {
		this.setPreferredSize(new Dimension(80, 80));
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(22,10,22,10));
		urlInput = new TextFieldWithPrompt("", "Please enter a url");

		urlInput.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(urlInput.getText().length() == 7){
					urlInput.setAccepted(true);
				} else {
					urlInput.setAccepted(false);
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

		this.add(urlInput, BorderLayout.CENTER);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Theme.CONTROL_BAR_BORDER);
		g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

	}
}
