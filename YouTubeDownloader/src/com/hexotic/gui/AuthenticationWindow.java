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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.border.DropShadowBorder;

import com.hexotic.lib.resource.Resources;
import com.hexotic.utils.Settings;
import com.hexotic.utils.XButton;

public class AuthenticationWindow extends JFrame{
	
	public static void main(String[] args){
		new AuthenticationWindow();
	}
	
	public AuthenticationWindow(){
		setTitle("Options");
		setUndecorated(true);
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL iconPth   = cldr.getResource("images/icon.png");
		this.setIconImage(new ImageIcon(iconPth).getImage());
		this.setLayout(new BorderLayout());
		this.setContentPane(new AuthenticationPanel(this));
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

class AuthenticationPanel extends JPanel{
	private JFrame parent;
	private JTable table;
	public AuthenticationPanel(JFrame p){
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

        JLabel header = new JLabel("Site Authentication");
        header.setPreferredSize(new Dimension(450, 30));
        header.setFont(new Font("Arial", Font.BOLD, 19));
        this.add(header);
        
        final JLabel closeBtn = new JLabel("X");
        closeBtn.setPreferredSize(new Dimension(10,30));
        closeBtn.setVerticalAlignment(SwingConstants.TOP);
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
        this.add(closeBtn);
        
        
        JLabel seperator = new JLabel("If your downloads require you to login first, provide login info here:");
        seperator.setPreferredSize(new Dimension(480, 30));
        seperator.setHorizontalAlignment(SwingConstants.CENTER);
        seperator.setForeground(new Color(0xa0a0a0));
        this.add(seperator);
        
        JLabel add = new JLabel(new ImageIcon(Resources.getInstance().getImage("add.png")));
        add.setPreferredSize(new Dimension(460, 20));
        add.setHorizontalAlignment(JLabel.RIGHT);
        add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
        });
        this.add(add);
     
        table = new JTable(new DefaultTableModel(new Object[]{"Site", "Username", "Password"}, 0));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(460, 130));
        scroll.setBackground(new Color(0,0,0,0));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(0xdadada)));
        this.add(scroll);
        
        XButton saveAll = new XButton("Save Login Info");
        saveAll.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent e){
        		for(int i = 0 ; i < table.getRowCount(); i++){
        			String site = (String) table.getModel().getValueAt(i, 0);
        			String user = (String) table.getModel().getValueAt(i, 1);
        			String pass = (String) table.getModel().getValueAt(i, 2);
        			Settings.getInstance().saveProperty("auth."+site, user+","+pass);
        		}
        		parent.dispose();
        	}
        });
        this.add(saveAll);
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
