package com.hexotic.v2.gui.sidebar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.JXCollapsiblePane;

import com.hexotic.lib.switches.SwitchEvent;
import com.hexotic.lib.switches.SwitchListener;
import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.utils.Settings;
import com.hexotic.v2.gui.theme.Theme;

/**
 * This class provides a neat sliding sidebar thing where
 * all of the traditional "File" menu options will be located
 * This is where the user will find all the configurable options
 * 
 * @author Bradley Sheets
 *
 */
public class Sidebar extends JXCollapsiblePane {

	private static final long serialVersionUID = -9063386111354561300L;
	private List<SidebarItem> sidebarItems = new ArrayList<SidebarItem>();
	
	public Sidebar() {
		int i = 0;
		SidebarSwitch formatSwitch = new SidebarSwitch(i++, "Download as:", "Video", "Audio");
		formatSwitch.setState(Boolean.valueOf(Settings.getInstance().getProperty("audioFormat", "false")));
		formatSwitch.addSwitchListener(new SwitchListener(){
			@Override
			public void switchTriggered(SwitchEvent event) {
				if(event.getState() == SwitchEvent.ON){
					Settings.getInstance().saveProperty("audioFormat", "true");
				} else {
					Settings.getInstance().saveProperty("audioFormat", "false");
				}
			}
			
		});
		sidebarItems.add(formatSwitch);
		sidebarItems.add(new SidebarButton("Change Download Directory", i++));
		sidebarItems.add(new SidebarSwitch(i++, "quick paste:", "Off", "On"));
		sidebarItems.add(new SidebarSwitch(i++, "on complete:", "Keep", "Remove"));
		
		
		sidebarItems.add(new SidebarSwitch(i++, "Proxy:", "Disabled", "Enabled"));
		sidebarItems.add(new SidebarButton("Configure Proxy Settings", i++));
		
//		sidebarItems.add(new SidebarButton("Install Chrome Extension", i++));
		
		
		this.setCollapsed(true);
		this.setAnimated(true);
		this.setDirection(Direction.RIGHT);
		this.setContentPane(new SideBarPanel());
	}
	
	public void toggle() {
		this.setCollapsed(!this.isCollapsed());
	}
	
	
	class SideBarPanel extends JPanel {
		private static final long serialVersionUID = 8471609150957256466L;
		private JScrollPane scroller;
		private JPanel items;
		
		public SideBarPanel() {
			this.setBackground(Theme.DARK);
			this.setPreferredSize(new Dimension(200, 200));
			this.setLayout(new BorderLayout(0,0));
			
			items = new JPanel(new AnimatedGridLayout(0,0, true));
			
			for(SidebarItem item : sidebarItems){
				items.add(item);
			}
			items.setOpaque(false);
			
			scroller = new JScrollPane(items);
			scroller.getVerticalScrollBar().setUI(new SimpleScroller());
			scroller.getVerticalScrollBar().setPreferredSize(new Dimension(2,2));
			scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scroller.getVerticalScrollBar().setUnitIncrement(25);
			scroller.setBorder(BorderFactory.createEmptyBorder());
			scroller.setOpaque(false);
			// TODO get this fucking scroller color sorted out
			this.add(new LogoPanel(), BorderLayout.NORTH);
			this.add(items, BorderLayout.CENTER);
			this.add(new SidebarInfoPanel(), BorderLayout.SOUTH);
		}
		
		public JScrollPane getScroller(){
			return scroller;
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			int width = 10;
			
			GradientPaint gp1 = new GradientPaint(width, width, new Color(0,0,0,0), 0, width, Theme.DARK_SHADOW, true);
		    g2d.setPaint(gp1);
			g2d.fillRect(getWidth()-width, 0, width, getHeight());

		}
		
	}
}
