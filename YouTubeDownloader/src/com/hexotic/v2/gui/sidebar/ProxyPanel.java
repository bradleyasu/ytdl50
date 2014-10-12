package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import com.hexotic.lib.switches.BasicSwitch;
import com.hexotic.lib.switches.SwitchEvent;
import com.hexotic.lib.switches.SwitchListener;
import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.utils.Settings;
import com.hexotic.v2.downloader.popup.PopupFactory;
import com.hexotic.v2.gui.components.TextFieldWithPrompt;
import com.hexotic.v2.gui.theme.Theme;

public class ProxyPanel extends JPanel {

	private int width = 575;
	private int height = 180;
	
	
	private TextFieldWithPrompt portNumber;
	private TextFieldWithPrompt ipAddress;
	private boolean isHttps = false;
	
	public ProxyPanel() {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		
		JLabel label = new JLabel("Setup Proxy");
		label.setFont(Theme.CONTROL_BAR_FONT.deriveFont(18.0f));
		
		Dimension labelD = new Dimension(width-40, 30);
		label.setPreferredSize(labelD);
		
		JLabel infoLabel = new JLabel("A proxy will allow you to 'hide' your location so that you appear to be located elsewhere in the world.");
		infoLabel.setFont(Theme.SWITCH_FONT);
		infoLabel.setPreferredSize(labelD);
		
		BasicSwitch httpType = new BasicSwitch("http://", "https://", 100, 27, 0);
		httpType.setFont(Theme.SWITCH_FONT);
		httpType.setForeground(Theme.DARK);
		httpType.setBackground(Theme.MAIN_COLOR_FOUR);
		httpType.addSwitchListener(new SwitchListener(){
			@Override
			public void switchTriggered(SwitchEvent e) {
				isHttps = (e.getState() == SwitchEvent.ON);
			}
		});
		
		httpType.setState(Settings.getInstance().getProperty("proxyIsHttps", "false").equals("true"));
		
		
		// user input for IP address - load saved settings by default
		ipAddress = new TextFieldWithPrompt(Settings.getInstance().getProperty("proxyIP", ""), "Proxy IP Address");
		ipAddress.setPreferredSize(new Dimension(width-250, 30));
		ipAddress.setSelectionColor(Theme.MAIN_COLOR_FOUR);
		
		ipAddress.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
				ipAddress.setAccepted(InetAddressValidator.getInstance().isValid(ipAddress.getText()));
			}
		});
		
		// User input for Port number - load saved settings by default
		portNumber = new TextFieldWithPrompt(Settings.getInstance().getProperty("proxyPort", ""), "Port #");
		portNumber.setPreferredSize(new Dimension(80, 30));
		portNumber.setSelectionColor(Theme.MAIN_COLOR_FOUR);

		portNumber.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if(IntegerValidator.getInstance().isValid(portNumber.getText())){
					portNumber.setAccepted(IntegerValidator.getInstance().isInRange(Integer.parseInt(portNumber.getText().trim()), 0, 65535));
				} else {
					portNumber.setAccepted(false);
				}
			}
		});
		
		
		SoftButton saveBtn = new SoftButton("Save Proxy Settings");
		saveBtn.setPreferredSize(new Dimension(185, 20));
		saveBtn.setBackgroundColor(Theme.DARK);
		saveBtn.setForegroundColor(Theme.MAIN_BACKGROUND);
		saveBtn.setFont(Theme.CONTROL_BAR_FONT);
		saveBtn.setArc(0);
		
		saveBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Settings.getInstance().saveProperty("proxyIP", ipAddress.getText());
				Settings.getInstance().saveProperty("proxyPort", portNumber.getText());
				Settings.getInstance().saveProperty("proxyIsHttps", String.valueOf(isHttps));
				PopupFactory.getPopupWindow().propagateClose();
			}
		});
		
		JLabel margin = new JLabel("");
		margin.setPreferredSize(labelD);
		
		ipAddress.setAccepted(true);
		portNumber.setAccepted(true);
		this.add(label);
		this.add(infoLabel);
		this.add(httpType);
		this.add(ipAddress);
		this.add(portNumber);
		this.add(margin);
		this.add(saveBtn);
		
	}
}
