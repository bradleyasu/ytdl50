package com.hexotic.v2.gui.support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hexotic.lib.resource.Resources;
import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.v2.gui.components.TextFieldWithPrompt;
import com.hexotic.v2.gui.theme.Theme;

public class ContactPanel extends JPanel {

	private TextFieldWithPrompt email;
	private JEditorPane feedback;

	public ContactPanel() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(600, 400));

		this.setLayout(new FlowLayout());
		
		email = new TextFieldWithPrompt("", "Your email address (optional, but we can respond if you include it)");
		email.setPreferredSize(new Dimension(500, 30));

		SoftButton sendButton = new SoftButton("Send it!");
		sendButton.setBackgroundColor(Theme.DARK);
		sendButton.setFont(new Font("Arial", Font.BOLD, 12));
		sendButton.setArc(4);

		feedback = new JEditorPane();
		feedback.setPreferredSize(new Dimension(498, 250));

		JScrollPane scroller = new JScrollPane(feedback);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().setUI(new SimpleScroller());
		scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5,5));
		scroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));
		
		JLabel label = new JLabel("Send us feedback");
		label.setFont(Theme.CONTROL_BAR_FONT.deriveFont(18f));
		label.setPreferredSize(new Dimension(500, 30));
		
		
		this.add(label);
		this.add(email);
		this.add(scroller);
		this.add(sendButton);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.drawImage(Resources.getInstance().getImage("support/letter.png"),getWidth()-102, getHeight()-100, 100,100,null);

	}

}
