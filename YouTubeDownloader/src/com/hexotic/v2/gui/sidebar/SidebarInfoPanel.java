package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.v2.downloader.popup.PopupFactory;
import com.hexotic.v2.gui.support.AboutPanel;
import com.hexotic.v2.gui.support.ContactPanel;
import com.hexotic.v2.gui.theme.Theme;

public class SidebarInfoPanel extends JPanel {

	private List<SoftButton> buttons = new ArrayList<SoftButton>();

	public SidebarInfoPanel() {
		this.setPreferredSize(new Dimension(200, 70));
		this.setOpaque(false);

		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		SoftButton aboutBtn = new SoftButton("About");
		SoftButton releaseBtn = new SoftButton("Release Notes");
		SoftButton bugsBtn = new SoftButton("Send Feedback");

		aboutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupFactory.getPopupWindow().setPrompt(new AboutPanel());
			}
		});

		bugsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupFactory.getPopupWindow().setPrompt(new ContactPanel());
			}
		});

		buttons.add(aboutBtn);
		buttons.add(releaseBtn);
		buttons.add(bugsBtn);

		for (SoftButton button : buttons) {
			button.setPreferredSize(new Dimension(87, 25));
			button.setBackgroundColor(Theme.DARK);
			button.setForegroundColor(Theme.MAIN_BACKGROUND);
			button.setFont(Theme.CONTROL_BAR_FONT);
			button.setArc(0);

			this.add(button);
		}

		buttons.get(buttons.size() - 1).setPreferredSize(new Dimension(179, 25));

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		GradientPaint gp1 = new GradientPaint(0, 0, Theme.DARK, 0, getHeight(), Theme.DARK_SHADOW, true);

		g2d.setPaint(gp1);
		g2d.fillRect(0, 0, getWidth(), getHeight());

	}
}
