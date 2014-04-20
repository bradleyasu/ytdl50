package com.hexotic.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hexotic.utils.FormInput;
import com.hexotic.utils.XComboBox;

public class FormComboPanel extends JPanel implements FormInput{
	private XComboBox input;
	public FormComboPanel(String prompt, Object[] defaultData, Object defaultSelection){
		input = new XComboBox(defaultData);
		input.setSelectedItem(defaultSelection);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setOpaque(false);
		JLabel promptLbl = new JLabel(prompt);
		promptLbl.setPreferredSize(new Dimension(80,25));
		promptLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(promptLbl);
		this.add(input);		
		this.setPreferredSize(new Dimension(250, 30));
	}

	@Override
	public Object getInput() {
		return input.getSelectedItem();
	}
}
