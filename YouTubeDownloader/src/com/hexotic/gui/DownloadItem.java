package com.hexotic.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URL;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import com.hexotic.cons.Constants;
import com.hexotic.utils.CentralDownloadControl;
import com.hexotic.utils.Downloader;
import com.hexotic.utils.VerticalWrapLayout;

public class DownloadItem extends JXPanel implements Runnable, Comparable<DownloadItem>{
	private String url;
	private JLabel thumbnail;
	private JProgressBar prog;
	private JLabel titleLabel;
	private JLabel urlLabel;
	private Downloader downloader;
	private CardLayout panels = new CardLayout();
	private JPanel control; 
	private ClassLoader cldr = this.getClass().getClassLoader();
	private boolean mp3Format = false;
	private int id;
	
	public DownloadItem(String url){
		this.id = CentralDownloadControl.getInstance().nextId();
		mp3Format = CentralDownloadControl.getInstance().downloadAsMP3();
		this.setOpaque(false);
		if(!CentralDownloadControl.getInstance().isValid(url)){
			url = "ytsearch:"+url;
		}
		this.url = url;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setPreferredSize(new Dimension(320, 110));
		setBorder(false);
		this.setBackground(Color.WHITE);
		
		java.net.URL defaultThumb   = cldr.getResource("images/loadingThumbnail.gif");
		thumbnail = new JLabel(new ImageIcon(defaultThumb));
		this.add(thumbnail);

		JPanel infoPanel = new JPanel(new VerticalWrapLayout());
		titleLabel = new JLabel("Loading....");
		
		
		infoPanel.add(titleLabel);
		titleLabel.setFont(new Font("Arial",Font.BOLD, 14));
		titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		titleLabel.setPreferredSize(new Dimension(170, 15));

		
		if(this.url.contains("ytsearch")){
			urlLabel = new JLabel("Smart Download");
			urlLabel.setForeground(new Color(0x484848));
		}else{
			urlLabel = new JLabel(this.url);
			urlLabel.setForeground(Color.BLUE);
			urlLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		urlLabel.setFont(new Font("Arial",Font.PLAIN, 10));
		urlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		urlLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		urlLabel.setPreferredSize(new Dimension(170, 14));
		urlLabel.setToolTipText(getUrl());
		urlLabel.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
					desktop.browse(new URI(getUrl()));
				} catch (Exception e){
					new MessageBox(Constants.MSG_5, "Your computer sucks", "I couldn't launch the browser",e.toString());
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		
		infoPanel.add(urlLabel);
		
		JLabel spaceLabel = new JLabel();
		spaceLabel.setPreferredSize(new Dimension(170,15));
		infoPanel.add(spaceLabel);
		infoPanel.setBackground(Color.WHITE);
				

		infoPanel.add(getProgControl());
		
		
		infoPanel.setPreferredSize(new Dimension(180, 90));
		this.add(infoPanel);
		
		
		downloader = new Downloader(this);
	}
	
	private JPanel getProgControl(){
		control = new JPanel(panels);
		
		/* READY PANEL */
		JPanel readyPanel = new JPanel(new FlowLayout());
		readyPanel.setBackground(Color.WHITE);
		Random rand = new Random();
		readyPanel.add(new JLabel(Constants.PHRASES[rand.nextInt(Constants.PHRASES.length)]));
		
		/* PROGRESS PANEL */
		JPanel progPanel = new JPanel(new FlowLayout());
		progPanel.setBackground(Color.WHITE);
		prog = new JProgressBar();
		prog.setBorder(BorderFactory.createLineBorder(new Color(40,40,40)));
		setStatus(0);
	    prog.setStringPainted(true);
	    prog.setPreferredSize(new Dimension(140, 20));
	    
	    java.net.URL canUrl   = cldr.getResource("images/cancel_dark.png");
		ImageIcon cancelIco = new ImageIcon(canUrl);
	    JLabel cancel = new JLabel(cancelIco);
	    cancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    cancel.setToolTipText("Cancel Download");
	    cancel.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cancelDownload();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
	    });
	    
	    if(mp3Format){
	    	prog.setPreferredSize(new Dimension(130, 20));
		    java.net.URL tagUrl   = cldr.getResource("images/tag.png");
			ImageIcon tagIco = new ImageIcon(tagUrl);
		    JLabel tagBtn = new JLabel(tagIco);
		    tagBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    tagBtn.setToolTipText("Edit ID3 Tags");
		    tagBtn.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					new TagEditor(downloader);
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
		    });
		    progPanel.add(tagBtn);
	    }
	    
	    progPanel.add(prog);
	    progPanel.add(cancel);
	    
	    /* FINISHED PANEL */
	    JPanel completePanel = new JPanel(new FlowLayout());
	    completePanel.setBackground(Color.WHITE);
	    completePanel.add(new JLabel("Wooo! Finished "));
	    if(mp3Format){
	    	prog.setPreferredSize(new Dimension(130, 20));
		    java.net.URL tagUrlComplete   = cldr.getResource("images/tag.png");
			ImageIcon tagIcoComplete = new ImageIcon(tagUrlComplete);
		    JLabel tagBtnComplete = new JLabel(tagIcoComplete);
		    tagBtnComplete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    tagBtnComplete.setToolTipText("Edit ID3 Tags");
		    tagBtnComplete.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					new TagEditor(downloader);
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
		    });
		    completePanel.add(tagBtnComplete);
	    }else{
	    	java.net.URL smileUrl   = cldr.getResource("images/smile.png");
	    	ImageIcon smileIco = new ImageIcon(smileUrl);
	    	JLabel btn = new JLabel(smileIco);
	    	completePanel.add(btn);
	    }
	    
	    
	    
	    /* CANCELED PANEL */
	    JPanel cancelPanel = new JPanel(new FlowLayout());
	    cancelPanel.setBackground(Color.WHITE);
	    java.net.URL restartUrl   = cldr.getResource("images/restart.png");
		ImageIcon restartIco = new ImageIcon(restartUrl);
	    cancelPanel.add(new JLabel("Canceled "));
	    JLabel restartBtn = new JLabel(restartIco);
	    restartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    restartBtn.setToolTipText("Continue Download");
	    restartBtn.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				new Thread(downloader.getDownloadItem()).start();
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
	    cancelPanel.add(restartBtn);
	    
	    
	    
	    
	    control.add(Constants.READY, readyPanel);
	    control.add(Constants.INPROGRESS, progPanel);
	    control.add(Constants.COMPLETE, completePanel);
	    control.add(Constants.CANCELED, cancelPanel);
	  	setState(Constants.READY);
		return control;
	}
	
	public void cancelDownload(){
		downloader.cancel();
	}
		
	public void setState(String state){
		panels.show(control, state);
	}
	
	public void setStatus(int progress){
		prog.setValue(progress);
		this.revalidate();
		this.repaint();
	}
	
	public void setAsQueued(){
		java.net.URL thumb   = cldr.getResource("images/defaultThumbnail.png");
		ImageIcon icon = new ImageIcon(thumb);
		thumbnail.setIcon(icon);
		titleLabel.setText("Queued For Download");
	}
	
	@Override
	public void run() {
		try{
			java.net.URL thumb   = cldr.getResource("images/defaultThumbnail.png");
			ImageIcon icon = new ImageIcon(thumb);
			try{
				icon = new ImageIcon(new URL(downloader.getThumbnailUrl()));
			}catch(Exception e){ /* Just use default icon */ }
			Image img = icon.getImage() ;
			Image newimg = img.getScaledInstance( 120, 90,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			thumbnail.setIcon(icon);
			String title = downloader.getTitle();
			titleLabel.setText(title);
			titleLabel.setToolTipText(title);
			this.revalidate();
			this.repaint();
			downloader.download(mp3Format);
		}catch(Exception e){
			new MessageBox(Constants.MSG_2, "A Download Crashed", this.url,e.toString());
		}
	}

	public String getUrl(){
		return url;
	}
	
	private void setBorder(boolean active){
		if(active){
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
		}else{
			DropShadowBorder shadow = new DropShadowBorder();
	        shadow.setShadowColor(Color.BLACK);
	        shadow.setShowLeftShadow(true);
	        shadow.setShowRightShadow(true);
	        shadow.setShowBottomShadow(true);
	        shadow.setShowTopShadow(true);
	        this.setBorder(shadow);
		}
	}
	
	@Override
	public String toString(){
		return url;
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
		 Color[] colors = { new Color(255,34,102),
				 			new Color(255,85,51),
				 			new Color(255,68,85),
		 					new Color(255,0,153),
		 };

         for(int i = 2; i < getHeight()-57; i++){
        	 Random rand = new Random();
        	 int index = rand.nextInt(colors.length);
        	 g.setColor(colors[index]);
        	 g.fillRect(4, i*2, 2, 2);
         }
         
	 }

	public int getId(){
		return id;
	}
	 
	@Override
	public int compareTo(DownloadItem o) {
		return o.getId() - this.id;
	}
}
