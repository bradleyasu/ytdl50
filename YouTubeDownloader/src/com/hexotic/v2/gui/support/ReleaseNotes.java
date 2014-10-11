package com.hexotic.v2.gui.support;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.jdesktop.swingx.JXCollapsiblePane;

import com.hexotic.lib.resource.Resources;
import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.v2.gui.theme.Theme;

public class ReleaseNotes extends JPanel {

	private static final long serialVersionUID = 4376712195634925368L;

	private JPanel notesContainer;
	private Image icon;
	
	public ReleaseNotes() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(600, 400));
		
		notesContainer = new JPanel(new AnimatedGridLayout());
		notesContainer.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		
		JScrollPane scroller = new JScrollPane(notesContainer);
		scroller.setPreferredSize(new Dimension(550,350));
		scroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().setUI(new SimpleScroller());
		scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5,5));
		scroller.getVerticalScrollBar().setUnitIncrement(25);
		

		try {
			importNotes();
		} catch (InvalidFileFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.add(scroller);
		icon = Resources.getInstance().getImage("icon_small.png");
	}
	
	private void importNotes() throws InvalidFileFormatException, FileNotFoundException, IOException {
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL releaseNotes   = cldr.getResource("docs/releaseNotes.ini");
		Ini ini = new Ini(new FileReader(releaseNotes.getPath()+"\\"+releaseNotes.getFile()));
    	System.out.println("Number of sections: "+ini.size()+"\n");
    	for (String sectionName: ini.keySet()) {
    		System.out.println("["+sectionName+"]");
    		Section section = ini.get(sectionName);
    		for (String optionKey: section.keySet()) {
    			System.out.println("\t"+optionKey+"="+section.get(optionKey));
    		}
    	}
		
		
	}
	
	class ReleaseNote extends JPanel{
		private JXCollapsiblePane infoPane;
		private JEditorPane editor;
		
		public ReleaseNote(String title){
			this.setLayout(new BorderLayout());
			ReleaseNoteTitle noteTitle = new ReleaseNoteTitle("5.1.0", "Lake Laogai", "Oct 24, 2014");
			noteTitle.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent e) {
					infoPane.setCollapsed(!infoPane.isCollapsed());
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
			this.add(noteTitle, BorderLayout.NORTH);
			this.setBackground(Theme.CONTROL_BAR_BORDER);
			
			infoPane = new JXCollapsiblePane();
			JPanel contentPanel = new JPanel(new BorderLayout());
			contentPanel.setPreferredSize(new Dimension(520, 200));
			contentPanel.setBackground(Theme.CONTROL_BAR_BACKGROUND);
			editor = new JEditorPane();
			editor.setEditable(false);
			editor.setBackground(Theme.CONTROL_BAR_BACKGROUND);
			
			JScrollPane editorScroller = new JScrollPane(editor);
			editorScroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));
			editorScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			editorScroller.getVerticalScrollBar().setUI(new SimpleScroller());
			editorScroller.getVerticalScrollBar().setPreferredSize(new Dimension(5,5));
			editorScroller.getVerticalScrollBar().setUnitIncrement(25);
			editorScroller.setBackground(Theme.CONTROL_BAR_BACKGROUND);
			contentPanel.add(editorScroller);
			
			editor.setText("Hey Guys! \n\n Welcome to version 5.1.0\n\n + New Interface");
			
			infoPane.setCollapsed(true);
			infoPane.setAnimated(true);
			infoPane.setContentPane(contentPanel);
			
			
			
			this.add(infoPane, BorderLayout.CENTER);
			
		}
	}
	
	
	class ReleaseNoteTitle extends JPanel {
		private String version;
		private String name;
		private String date;
		
		public ReleaseNoteTitle(String version, String name, String date) {
			this.version = version;
			this.name = name;
			this.date = date;
			this.setPreferredSize(new Dimension(30,30));
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			this.setBackground(Theme.TRANSPARENT);
			this.add(new JLabel(""));
		}
		
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


			GradientPaint gp1 = new GradientPaint(getWidth(), getWidth(), Theme.DARK, 0, getWidth(), Theme.DARK_SHADOW, true);
			g2d.setPaint(gp1);
			g2d.fillRoundRect(0, 0, getWidth(), getHeight()+10, 5,5);
			
			g2d.drawImage(icon, 2,-2,null);
			
			g2d.setPaint(Theme.CONTROL_BAR_BACKGROUND);
			g2d.setFont(Theme.CONTROL_BAR_FONT.deriveFont(14.0f));
			g2d.drawString(name+" ("+version+")", 40,20);
			
			g2d.setFont(Theme.CONTROL_BAR_FONT.deriveFont(11.0f));
			g2d.drawString(date, getWidth()-70,25);

		}
	}
	
}
