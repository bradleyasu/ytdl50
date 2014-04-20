package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.utils.CentralDownloadControl;
import com.hexotic.utils.XHorizontalScrollBar;

public class MainContainer extends JPanel{
	private DownloadContainer downloadContainer = new DownloadContainer();
	public MainContainer(){		
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);
		JScrollPane scroller = new JScrollPane(downloadContainer);
		scroller.setBorder(BorderFactory.createEmptyBorder());
		scroller.setHorizontalScrollBar(new XHorizontalScrollBar());
		scroller.getHorizontalScrollBar().setUI(new BasicScrollBarUI()
	    {   
	        @Override
			protected JButton createDecreaseButton(int orientation) {
	            return createButton();
	        }
	        @Override
			protected JButton createIncreaseButton(int orientation) {
	            return createButton();
	        }
	        private JButton createButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0));
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
	    });
		this.add(scroller, BorderLayout.CENTER);
	}
	
}

class DownloadContainer extends JPanel implements Observer{
	private ExecutorService pool = Executors.newFixedThreadPool(16);
	public DownloadContainer(){
		this.setBackground(Color.WHITE);
		//this.setLayout(new VerticalWrapLayout(VerticalFlowLayout.TOP));
		AnimatedGridLayout layout = new AnimatedGridLayout(true);
		this.setLayout(layout);
		CentralDownloadControl.getInstance().addObserver(this);
		this.setOpaque(false);
		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof DownloadItem){
			DownloadItem download = CentralDownloadControl.getInstance().getLast();
			this.add(CentralDownloadControl.getInstance().getLast());
			this.revalidate();
			this.repaint();
			pool.execute(download);
			download.setAsQueued();
			//new Thread(download).start();
			System.out.println("Downloading: "+arg.toString());
		}
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		 super.paintComponent(g);
         Graphics2D g2d = (Graphics2D) g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                 RenderingHints.VALUE_ANTIALIAS_ON);

         GradientPaint gp = new GradientPaint(0, 0,
                 new Color(250,250,250),
                 200, getHeight(),
                 Color.WHITE);

         g2d.setPaint(gp);
         g2d.fillRect(0, 0, getWidth(), getHeight());
         g.setColor(new Color(255,255,255));
         g.drawLine(0, 0, getWidth(), 0);
         g.setColor(new Color(240,240,240));
         g.drawLine(0, 1, getWidth(), 1);
	 }

}