package com.hexotic.v2.gui.primary.downloaditem;

import javax.swing.JPanel;
import com.hexotic.v2.gui.theme.Theme;

public class ItemMenu extends JPanel{

	public ItemMenu(Item item){
		this.setBackground(Theme.MAIN_COLOR_FOUR);
		this.setPreferredSize(item.getPreferredSize());
	}
}
