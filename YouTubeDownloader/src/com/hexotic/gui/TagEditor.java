package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.jdesktop.swingx.border.DropShadowBorder;

import com.hexotic.utils.Downloader;
import com.hexotic.utils.FormInput;
import com.hexotic.utils.VerticalWrapLayout;
import com.hexotic.utils.XButton;

public class TagEditor extends JFrame{
	
	public static void main(String args[]){
		ArrayList<Object> gradients = new ArrayList<Object>(5);
		gradients.add(0.28f);
		gradients.add(0.00f);
		gradients.add(new Color(0xe1e1e1));
		gradients.add(new Color(0xefefef));
		gradients.add(new Color(0xf7f7f7));
		UIManager.put( "ScrollBar.background", new Color(0xe1e1e1) );
		UIManager.put( "ScrollBar.darkShadow", new Color(0xdadada) );	
		UIManager.put( "ScrollBar.highlight", new Color(0xd8d8d8) );
		UIManager.put( "ScrollBar.shadow", new Color(0xd0d0d0) );
		UIManager.put( "ScrollBar.thumbShadow", new Color(0xd0d0d0) );
		UIManager.put( "ScrollBar.thumbHighlight", new Color(0xe7e7e7) );
		UIManager.put( "ScrollBar.gradient", gradients );
		UIManager.put( "control", new Color(0xe1e1e1) );
		new TagEditor(new Downloader(new DownloadItem("http://www.youtube.com/watch?v=gHGNtWv_90w")));
	}
	
	Downloader downloader;
	public TagEditor(Downloader downloader){
		this.downloader = downloader;
		setTitle("Tag Editor");
		setUndecorated(true);
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL iconPth   = cldr.getResource("images/icon.png");
		this.setIconImage(new ImageIcon(iconPth).getImage());
		this.setLayout(new BorderLayout());
		this.setContentPane(new TagEditorPanel(this));
		this.setBackground(new Color(0, 255, 0, 0));
		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(new Dimension(550, 285));
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		this.setLocation(x, y);
		this.setVisible(true);
	}
	
	public Downloader getDownloader(){
		return downloader;
	}
}

class TagEditorPanel extends JPanel{
	private TagEditor parent;
	private Downloader downloader;
	
	private FormInput titleInput;
	private FormInput artistInput;
	private FormInput albumInput;
	private FormInput commentsInput;
	private FormInput yearInput;
	private FormInput genreInput;
	
	
	public TagEditorPanel(TagEditor p){
		parent = p;
		downloader = parent.getDownloader();
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
        this.setLayout(new BorderLayout());
        String downloadTitle = "";
        try {
        	downloadTitle = downloader.getTitle();
		} catch (IOException e) {
			e.printStackTrace();
		}
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        JLabel header = new JLabel(" ID3 Tags");
        header.setPreferredSize(new Dimension(520, 30));
        header.setFont(new Font("Arial", Font.BOLD, 24));
        final JLabel closeBtn = new JLabel("X");
        closeBtn.setVerticalAlignment(SwingConstants.TOP);
        closeBtn.setPreferredSize(new Dimension(12,30));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.setForeground(new Color(0xababab));
        closeBtn.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.dispose();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				closeBtn.setForeground(new Color(0x484848));
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				closeBtn.setForeground(new Color(0xababab));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
        	
        });
        headerPanel.add(header);
        headerPanel.add(closeBtn);
        headerPanel.setOpaque(false);
        this.add(headerPanel, BorderLayout.NORTH);
        
        JLabel footer = new JLabel(downloadTitle);
        footer.setPreferredSize(new Dimension(550, 15));
        footer.setHorizontalAlignment(SwingConstants.RIGHT);
        footer.setForeground(new Color(0xc2c2c2));
        this.add(footer, BorderLayout.SOUTH);
       
        JPanel formPanel = new JPanel(new VerticalWrapLayout());
        Object[] currentTags = downloader.getTagger().getTags(downloadTitle);
        titleInput = new FormInputPanel("Title", (String)currentTags[0]);
        artistInput = new FormInputPanel("Artist", (String)currentTags[1]);
        albumInput = new FormInputPanel("Album", (String)currentTags[2]);
        yearInput = new FormInputPanel("Year", (String)currentTags[3]);
        
        genreInput = new FormComboPanel("Genre", downloader.getTagger().getGenres(),currentTags[4]);
        ((Component)genreInput).setPreferredSize(new Dimension(505,30));
        commentsInput = new FormTextAreaPanel("Comments", (String)currentTags[5]);
        formPanel.add((Component) titleInput);
        formPanel.add((Component) artistInput);
        formPanel.add((Component) albumInput);
        formPanel.add((Component) yearInput);
        formPanel.add((Component) genreInput);
        formPanel.add((Component) commentsInput);
        
        
        XButton saveTags = new XButton("Save Tags");
        saveTags.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent e){
        		downloader.applyTags((String)albumInput.getInput(), 
			        				(String)artistInput.getInput(), 
			        				(String)commentsInput.getInput(), 
			        				(String)titleInput.getInput(), 
			        				(String)yearInput.getInput(), 
			        				(ID3V1Tag.Genre)genreInput.getInput());
        		parent.dispose();
        	}
        });
        formPanel.add(saveTags);
        
        formPanel.setOpaque(false);
        this.add(formPanel);
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
}