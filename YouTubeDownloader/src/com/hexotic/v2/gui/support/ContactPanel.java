package com.hexotic.v2.gui.support;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.validator.routines.EmailValidator;

import com.hexotic.lib.resource.Resources;
import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.downloader.popup.PopupFactory;
import com.hexotic.v2.gui.components.TextFieldWithPrompt;
import com.hexotic.v2.gui.theme.Theme;

public class ContactPanel extends JPanel {

	private static final long serialVersionUID = -8754064883335870669L;
	private TextFieldWithPrompt email;
	private JEditorPane feedback;
	private EmailValidator emailValidator = EmailValidator.getInstance();

	public ContactPanel() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(600, 400));

		this.setLayout(new FlowLayout());

		email = new TextFieldWithPrompt("", "Your email address (optional, but we can respond if you include it)");
		email.setPreferredSize(new Dimension(500, 30));

		email.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
				email.setAccepted(emailValidator.isValid(email.getText()));
			}
		});

		SoftButton sendButton = new SoftButton("Send it!");
		sendButton.setBackgroundColor(Theme.DARK);
		sendButton.setFont(new Font("Arial", Font.BOLD, 12));
		sendButton.setArc(4);

		feedback = new JEditorPane();
		feedback.setPreferredSize(new Dimension(498, 250));

		JScrollPane scroller = new JScrollPane(feedback);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().setUI(new SimpleScroller());
		scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5, 5));
		scroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));

		JLabel label = new JLabel("Send us feedback");
		label.setFont(Theme.CONTROL_BAR_FONT.deriveFont(18f));
		label.setPreferredSize(new Dimension(500, 30));

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PopupFactory.getPopupWindow().propagateClose();
				// Send email in background - initial conneciton could be slow
				new Thread(new Runnable() {
					@Override
					public void run() {
						// Only send the email if they actually included
						// something
						if (feedback.getText().trim().length() > 0) {
							sendMail();
						}
					}
				}).start();
				;
			}
		});

		this.add(label);
		this.add(email);
		this.add(scroller);
		this.add(sendButton);
	}

	private void sendMail() {
		String to = "support@hexotic.net";
		String from = "support@hexotic.net";
		if (!email.getText().isEmpty()) {
			from = email.getText();
		}
		String host = "mail.hexotic.net";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(properties);
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Youtube Downloader Feedback and Support");
			message.setText(feedback.getText());
			Transport.send(message);
		} catch (MessagingException mex) {
			Log.getInstance().error(this, "Failed to send email", mex);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Resources.getInstance().getImage("support/letter.png"), getWidth() - 102, getHeight() - 100, 100, 100, null);

	}

}
