package com.hexotic.v2.downloader.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXCollapsiblePane;

import com.hexotic.v2.gui.theme.Theme;

public class GlassPane extends JPanel {

	private static final long serialVersionUID = 3240132644812244453L;
	private JXCollapsiblePane content;

	private boolean focused = false;

	public GlassPane() {
		this.setBackground(new Color(0, 0, 0, 80));
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		content = new JXCollapsiblePane();
		content.setAnimated(true);
		content.setCollapsed(true);

		content.setFocusable(true);
		content.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					close();
				}
			}
		});

		JPanel p = new JPanel(new BorderLayout(0, 0));
		p.add(content);

		p.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				focused = true;
			}

			@Override
			public void mouseExited(MouseEvent e) {
				focused = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		p.add(getHeader(), BorderLayout.NORTH);
		p.add(getFooter(), BorderLayout.SOUTH);
		p.setOpaque(false);
		this.add(p);
	}

	public void setPrompt(JPanel panel) {
		content.setContentPane(panel);
		content.setCollapsed(false);
		content.requestFocus();

	}

	private JPanel getHeader() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		panel.setPreferredSize(new Dimension(25,25));
		JLabel close = new JLabel("X");
		close.setForeground(Theme.CONTROL_BAR_BORDER.darker());
		close.setPreferredSize(new Dimension(20,20));
		close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				close();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		panel.add(close, BorderLayout.EAST);
		return panel;
	}

	private JPanel getFooter() {
		JPanel panel = new JPanel();
		panel.setBackground(Theme.MAIN_COLOR_FOUR);
		panel.setPreferredSize(new Dimension(2, 2));
		return panel;
	}

	public boolean isPopupFocused() {
		return focused;
	}

	public void close() {
		content.setCollapsed(true);
		PopupFactory.getPopupWindow().setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		GradientPaint gp1 = new GradientPaint(0, 0, new Color(0, 0, 0, 175), 0, 50, new Color(0, 0, 0, 0), true);

		g2d.setPaint(gp1);
		g2d.fillRect(0, 0, getWidth(), 50);

	}

}
