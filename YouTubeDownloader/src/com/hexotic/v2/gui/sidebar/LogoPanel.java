package com.hexotic.v2.gui.sidebar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;

import com.hexotic.cons.Constants;
import com.hexotic.lib.resource.Resources;
import com.hexotic.v2.gui.theme.Theme;

public class LogoPanel extends JPanel{
	private Image img = Resources.getInstance().getImage("icon_small.png");
	private int clickCount = 0;
	private boolean easterEgg = false;
	private Random random;
	private String[] doge = {"much download", "wow!", "so wow!", "such download", "such wow!", "wow"};
	
	public LogoPanel(){
		this.setPreferredSize(new Dimension(200, 50));
		
		this.setOpaque(false);
		
		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(clickCount == 10){
					img = Resources.getInstance().getImage("doge.png");
					easterEgg = true;
					random = new Random();
				}
				clickCount++;
				update();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}
	
	private void update() {
		this.revalidate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		g2d.drawImage(img, 10, 10, null);
		
		g2d.setColor(Theme.MAIN_BACKGROUND);
		g2d.setFont(Theme.CONTROL_BAR_FONT);
		g2d.drawString(Constants.PROG_NAME, 50, 26);
		
		g2d.setFont(Theme.SWITCH_FONT);
		g2d.drawString("Version: "+Constants.VERSION, 50, 36);
		
		if(easterEgg){
			g2d.setColor(Theme.MAIN_COLOR_FOUR);
			g2d.drawLine(50, 33, 115, 33);
			g2d.drawString(doge[random.nextInt(doge.length)], 118, 45);
		}
	}
}
