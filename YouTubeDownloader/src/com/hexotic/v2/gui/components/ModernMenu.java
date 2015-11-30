package com.hexotic.v2.gui.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.hexotic.v2.gui.theme.Theme;

public class ModernMenu extends JPanel{

	private List<ModernMenuItem> items;
	private int selectedIndex = 0;
	
	public ModernMenu() {
		items = new ArrayList<ModernMenuItem>();
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.setPreferredSize(new Dimension(100, 50));
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
	}
	
	public void addMenuItem(ModernMenuItem item) {
		this.add(item);
		if(items.isEmpty()){
			item.setSelected(true);
		}
		item.addMenuItemListener(new ModernMenuItemListener(){
			@Override
			public void itemSelected(String label, ModernMenuItem item) {
				for(ModernMenuItem i :items){
					i.setSelected(false);
				}
				item.setSelected(true);
				revalidate();
				repaint();
			}
		});
		items.add(item);
	}
	
}
