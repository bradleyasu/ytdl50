package com.hexotic.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hexotic.utils.FormInput;
import com.hexotic.utils.XInputBox;
import com.hexotic.utils.XTextArea;

public class FormTextAreaPanel extends JPanel implements FormInput{
	private XTextArea input;
	public FormTextAreaPanel(String prompt, String defaultText){
		input = new XTextArea(defaultText);
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		JLabel promptLbl = new JLabel(prompt);
		promptLbl.setPreferredSize(new Dimension(450,20));
		promptLbl.setHorizontalAlignment(JLabel.LEFT);
		((FlowLayout)this.getLayout()).setVgap(0);
		this.add(promptLbl);
		this.add(input);		
		this.setPreferredSize(new Dimension(450, 80));
	}

	public String getInput() {
		return input.getText();
	}
}
