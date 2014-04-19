package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.border.DropShadowBorder;

import com.hexotic.cons.Constants;
import com.hexotic.lib.audio.SoundFX;
import com.hexotic.lib.ui.notificationbar.Notification;
import com.hexotic.lib.ui.notificationbar.NotificationBar;
import com.hexotic.utils.CentralDownloadControl;
import com.hexotic.utils.Settings;
import com.hexotic.utils.XButton;

public class OptionsWindow extends JFrame{
	
	public static void main(String[] args){
		new OptionsWindow();
	}
	
	public OptionsWindow(){
		setTitle("Options");
		setUndecorated(true);
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL iconPth   = cldr.getResource("images/icon.png");
		this.setIconImage(new ImageIcon(iconPth).getImage());
		this.setLayout(new BorderLayout());
		this.setContentPane(new OptionsPanel(this));
		this.setBackground(new Color(0, 255, 0, 0));
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(500, 305));
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		this.setLocation(x, y);
		this.setVisible(true);
	}
}

class OptionsPanel extends JPanel{
	private JFrame parent;
	private FormFolderChooserPanel folderChooser;
	public OptionsPanel(JFrame p){
		parent = p;
		this.setBackground(new Color(0xffffff));
		this.setBorder(BorderFactory.createLineBorder(new Color(0x212121)));
		DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(Color.BLACK);
        shadow.setShowLeftShadow(true);
        shadow.setShowRightShadow(true);
        shadow.setShowBottomShadow(true);
        shadow.setShowTopShadow(true);
        this.setBorder(shadow);
        setOpaque(true);
        this.setLayout(new FlowLayout());

        JLabel header = new JLabel("Options");
        header.setPreferredSize(new Dimension(450, 30));
        header.setFont(new Font("Arial", Font.BOLD, 22));
        this.add(header);
        
        final JLabel closeBtn = new JLabel("X");
        closeBtn.setPreferredSize(new Dimension(10,30));
        closeBtn.setVerticalAlignment(JLabel.TOP);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.setForeground(new Color(0xababab));
        closeBtn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {
				parent.dispose();
			}
			public void mouseEntered(MouseEvent arg0) {
				closeBtn.setForeground(new Color(0x484848));
			}
			public void mouseExited(MouseEvent arg0) {
				closeBtn.setForeground(new Color(0xababab));
			}
			public void mousePressed(MouseEvent arg0) {
			}
			public void mouseReleased(MouseEvent arg0) {
			}
        	
        });
        this.add(closeBtn);
        
        
        JLabel seperator = new JLabel(Constants.PROG_NAME+" version: "+Constants.VERSION);
        seperator.setPreferredSize(new Dimension(480, 30));
        seperator.setHorizontalAlignment(JLabel.CENTER);
        seperator.setForeground(new Color(0xa0a0a0));
        this.add(seperator);

        
        
//        String downloadFormat = Settings.getInstance().getProperty("defaultDownloadMode", "video");
        
        final SwitchForm autoremoveSwitch = new SwitchForm("Automatically remove completed downloads?", false);
        this.add(autoremoveSwitch);
        
        final SwitchForm animationSwitch = new SwitchForm("Enable UI animations?", false);
        this.add(animationSwitch);
        
        
        String downloadFormat = Settings.getInstance().getProperty("defaultDownloadMode", "video");
        final SwitchForm asMp3Switch = new SwitchForm("Download videos in MP3 format by default?", downloadFormat.equals("video") ? false : true);
        this.add(asMp3Switch);
        
        folderChooser = new FormFolderChooserPanel("Download To:", CentralDownloadControl.getInstance().getDownloadDirectory());
        this.add(folderChooser);
        
        JLabel spacer = new JLabel("");
        spacer.setPreferredSize(new Dimension(480, 30));
        spacer.setHorizontalAlignment(JLabel.CENTER);
        spacer.setForeground(new Color(0xa0a0a0));
        this.add(spacer);
        
        XButton saveAll = new XButton("Save All");
        saveAll.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		CentralDownloadControl.getInstance().setDownloadDirectory(folderChooser.getInput());
        		
        		boolean downloadAsMp3 = asMp3Switch.isSet();
        		Settings.getInstance().saveProperty("defaultDownloadMode", downloadAsMp3 ? "audio" : "video");
        		parent.dispose();
        	}
        });
        this.add(saveAll);
	}
	
	 protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		
        g2d.setPaint(new GradientPaint(new Point(0, 0), new Color(0xf8f8f8), new Point(0,
                getHeight()), new Color(0xfefefe)));  
		g2d.fillRect(5,5, getWidth()-10, getHeight()-10);
		
		g2d.dispose();
		
		Color[] colors = { new Color(255,34,102),
		 			new Color(255,85,51),
		 			new Color(255,68,85),
					new Color(255,0,153),
		};
		
		for(int i = 2; i < (getWidth()/2)-6; i++){
			 Random rand = new Random();
			 int index = rand.nextInt(colors.length);
			 g.setColor(colors[index]);
			 g.fillRect(i*2, getHeight()-7, 10, 2);
		}
	}
}
