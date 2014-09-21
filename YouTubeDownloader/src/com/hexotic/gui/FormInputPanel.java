package com.hexotic.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hexotic.utils.FormInput;
import com.hexotic.utils.XInputBox;

public class FormInputPanel extends JPanel implements FormInput{
	private XInputBox input;
	public FormInputPanel(String prompt, String defaultText){
		input = new XInputBox(defaultText);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setOpaque(false);
		JLabel promptLbl = new JLabel(prompt);
		promptLbl.setPreferredSize(new Dimension(80,25));
		promptLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(promptLbl);
		this.add(input);		
		this.setPreferredSize(new Dimension(800, 30));
	}

	@Override
	public String getInput() {
		return input.getText();
	}
}
