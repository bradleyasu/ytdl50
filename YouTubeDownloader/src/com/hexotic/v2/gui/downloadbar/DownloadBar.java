package com.hexotic.v2.gui.downloadbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.v2.gui.components.TextFieldWithPrompt;
import com.hexotic.v2.gui.theme.Theme;

/**
 * The download bar is the top panel that will have a button to toggle sidebar,
 * input box for pasting a URL, and a download button
 * 
 * @author Bradley Sheets
 * 
 */
public class DownloadBar extends JPanel implements Runnable{

	private static final long serialVersionUID = 5261700324127818276L;
	private TextFieldWithPrompt urlInput;

	private SoftButton downloadButton;
	
	private int bannerX = -10;

	private List<DownloadBarListener> listeners = new ArrayList<DownloadBarListener>();

	public DownloadBar() {
		this.setPreferredSize(new Dimension(80, 80));
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(24, 22, 24, 12));
		urlInput = new TextFieldWithPrompt("", "Paste in a URL and click that fancy download button to the right");
		urlInput.setSelectionColor(Theme.MAIN_COLOR_FOUR);
		
		JPanel downloadButtonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
		downloadButtonContainer.setOpaque(false);

		downloadButton = new SoftButton("Download");
		downloadButton.setBackgroundColor(Theme.DARK);
		downloadButton.setFont(new Font("Arial", Font.BOLD, 12));
		downloadButton.setArc(4);
		downloadButtonContainer.add(downloadButton);

		downloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyListeners(urlInput.getText());
				urlInput.setText("");
			}
		});

		urlInput.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					notifyListeners(urlInput.getText());
					urlInput.setText("");
					urlInput.requestFocus();
				}
				if (urlInput.getText().length() == 7) {
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
		this.add(downloadButtonContainer, BorderLayout.EAST);
	}

	public void addDownloadBarListener(DownloadBarListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners(String input) {
		input = input.trim();
		/* Only download if something was actually entered*/
		if (!input.isEmpty()) {
			for (DownloadBarListener listener : listeners) {
				listener.inputEntered(input);
			}
		}
	}
	
	
	@Override
	public void run(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
		}
		while(this.bannerX < 4){
			try {
				Thread.sleep(20);
				bannerX++;
				revalidate();
				repaint();
			} catch (InterruptedException e) {
			}			
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Theme.CONTROL_BAR_BORDER);
		g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

		int width = 10;
		int y = 0;
		g2d.setColor(Theme.DARK);
		g2d.fillRect(bannerX > 0? 0 : bannerX, y += 10, width, width);

		g2d.setColor(Theme.MAIN_COLOR_ONE);
		g2d.fillRect(bannerX > 0? 0 : bannerX-1, y += 10, width, width);
		g2d.setColor(Theme.MAIN_COLOR_TWO);
		g2d.fillRect(bannerX > 0? 0 : bannerX-2, y += 10, width, width);
		g2d.setColor(Theme.MAIN_COLOR_THREE);
		g2d.fillRect(bannerX > 0? 0 : bannerX-3, y += 10, width, width);
		g2d.setColor(Theme.MAIN_COLOR_FOUR);
		g2d.fillRect(bannerX > 0? 0 : bannerX-4, y += 10, width, width);

	}
}
