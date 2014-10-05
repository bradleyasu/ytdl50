package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.utils.Settings;
import com.hexotic.v2.downloader.popup.PopupFactory;
import com.hexotic.v2.gui.components.TextFieldWithPrompt;
import com.hexotic.v2.gui.components.XTree;
import com.hexotic.v2.gui.theme.Theme;

public class ChangeDirectoryPanel extends JPanel {

	private int width = 400;
	private int height = 375;
	private XTree chooser;
	private TextFieldWithPrompt selectedDirectory;
	
	public ChangeDirectoryPanel() {
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		String current = Settings.getInstance().getProperty("downloadDir", desktop.getAbsolutePath());
		
		File currentFileCheck = new File(current);
		
		selectedDirectory = new TextFieldWithPrompt(current, "");
		selectedDirectory.setAccepted(currentFileCheck.exists());
		
		selectedDirectory.setPreferredSize(new Dimension(width-50, 30));
		selectedDirectory.setEditable(false);
		
		chooser = new XTree();
		JScrollPane scroller = new JScrollPane(chooser);
		scroller.getVerticalScrollBar().setUI(new SimpleScroller());
		scroller.getHorizontalScrollBar().setUI(new SimpleScroller());
		scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5,5));
		scroller.getHorizontalScrollBar().setPreferredSize(new Dimension(5,5));
		scroller.setPreferredSize(new Dimension(width-50, height-75));
		scroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));
		this.add(scroller);
		
		this.add(selectedDirectory);
		chooser.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				selectedDirectory.setText(chooser.getSelected());
				selectedDirectory.setAccepted(true);
			}
		});
		
		SoftButton saveChangesBtn = new SoftButton("Set as download directory");
		saveChangesBtn.setPreferredSize(new Dimension(175, 20));
		saveChangesBtn.setBackgroundColor(Theme.DARK);
		saveChangesBtn.setForegroundColor(Theme.MAIN_BACKGROUND);
		saveChangesBtn.setFont(Theme.CONTROL_BAR_FONT);
		saveChangesBtn.setArc(0);
		
		saveChangesBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Settings.getInstance().saveProperty("downloadDir", selectedDirectory.getText());
				PopupFactory.getPopupWindow().propagateClose();
			}
		});
		
		this.add(saveChangesBtn);
		
	}
}
