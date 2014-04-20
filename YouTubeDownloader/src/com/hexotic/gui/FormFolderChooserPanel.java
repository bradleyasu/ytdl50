package com.hexotic.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.hexotic.utils.FormInput;
import com.hexotic.utils.XDirectoryChooser;
import com.hexotic.utils.XInputBox;

public class FormFolderChooserPanel extends JPanel implements FormInput{
	private XInputBox input;
	private BrowseButton browse;
	public FormFolderChooserPanel(String prompt, String defaultText){
		input = new XInputBox(defaultText);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setOpaque(false);
		JLabel promptLbl = new JLabel(prompt);
		promptLbl.setPreferredSize(new Dimension(80,25));
		promptLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(promptLbl);
		this.add(input);
		input.setPreferredSize(new Dimension(320, 24));
		this.setPreferredSize(new Dimension(450, 30));
		input.setEditable(false);
		browse = new BrowseButton();
		browse.setPreferredSize(new Dimension(20,24));
		this.add(browse);
		browse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new XDirectoryChooser(input);
			}
		});
	}

	@Override
	public String getInput() {
		return input.getText();
	}
}

class BrowseButton extends JButton{
	public BrowseButton(){
		setFont(new Font("Arial", Font.BOLD, 1));
		setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black));
		setBorder(BorderFactory.createEmptyBorder());
        setPreferredSize(new Dimension(100, 23));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);

         Graphics2D g2 = (Graphics2D) g.create();
         g2.setPaint(new GradientPaint(new Point(0, 0), new Color(0xefefef), new Point(0,
                 getHeight()), new Color(0xe1e1e1)));         
         g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0,0);
         
         g2.setPaint(new Color(0xababab));
         
         for(int i = 0; i < 3; i++){
        	 g2.fillRect(this.getWidth()/2-2,4+(i*5)+(1*i), 5,5);
         }
         
         
         g2.dispose();
	 }
}
