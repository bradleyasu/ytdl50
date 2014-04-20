package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXEditorPane;
import org.jdesktop.swingx.border.DropShadowBorder;

import com.hexotic.utils.XButton;

public class MessageBox extends JFrame{
	
	private String title;
	private String message;
	private String info;
	private String icon;
	
	public MessageBox(String icon, String title, String message, String info){
		setTitle(title);
		this.title = title;
		this.message = message;
		this.info = info;
		this.icon = icon;
		setUndecorated(true);
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL iconPth   = cldr.getResource("images/icon.png");
		this.setIconImage(new ImageIcon(iconPth).getImage());
		this.setLayout(new BorderLayout());
		this.setContentPane(new MessagePanel(this));
		this.setBackground(new Color(0, 255, 0, 0));
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		if(info == null || info.equals("")){
			this.setSize(new Dimension(550, 175));
		}else{
			this.setSize(new Dimension(550, 250));
		}
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		this.setLocation(x, y);
		this.setVisible(true);
	}
	
	@Override
	public String getTitle(){
		return title;
	}
	public String getMessage(){
		return message;
	}
	public String getInfo(){
		return info;
	}
	public ImageIcon getIcon(){
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL thumb   = cldr.getResource(icon);
		ImageIcon ico = new ImageIcon(thumb);
		return ico;
	}
}

class MessagePanel extends JPanel{
	private MessageBox parent;
	public MessagePanel(MessageBox p){
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

		this.add(new JLabel(parent.getIcon()));
		JLabel titleLabel = new JLabel(parent.getTitle());
		titleLabel.setPreferredSize(new Dimension(470, 24));
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		this.add(titleLabel);
		
		JLabel infoLabel = new JLabel(parent.getMessage());
		infoLabel.setPreferredSize(new Dimension(450, 20));
		infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
		infoLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.add(infoLabel);
		
		if(parent.getInfo() != null && !(parent.getInfo().equals(""))){
			JXEditorPane info =new JXEditorPane();
			info.setText(parent.getInfo());
			info.setPreferredSize(new Dimension(450,125));
			info.setBorder(BorderFactory.createLineBorder(new Color(0xe0e0e0)));
			info.setEditable(false);
			this.add(info);
		}else{
			JLabel spaceLabel = new JLabel("");
			spaceLabel.setPreferredSize(new Dimension(500, 50));
			this.add(spaceLabel);
		}
		JLabel indentLabel = new JLabel("");
		indentLabel.setPreferredSize(new Dimension(344, 10));
		this.add(indentLabel);
		XButton close = new XButton("okay");
		close.setPreferredSize(new Dimension(100, 22));
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				parent.dispose();
			}
		});
		this.add(close);
		//playSound("tick.wav");
	}
	
	 @Override
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
	 
	 
	  private synchronized void playSound(final String url) {
		    new Thread(new Runnable() { 
		      @Override
			public void run() {
		        try {
		          Clip clip = AudioSystem.getClip();
		          ClassLoader cldr = this.getClass().getClassLoader();
		          AudioInputStream inputStream = AudioSystem.getAudioInputStream(cldr.getResourceAsStream("sounds/" + url));
		          clip.open(inputStream);
		          clip.start(); 
		        } catch (Exception e) {
		          System.err.println(e.getMessage());
		        }
		      }
		    }).start();
	  }
	 
}

