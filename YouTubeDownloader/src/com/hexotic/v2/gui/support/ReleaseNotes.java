package com.hexotic.v2.gui.support;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
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
import com.hexotic.lib.ui.buttons.SoftButton;
import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.v2.console.Log;
import com.hexotic.v2.downloader.popup.PopupFactory;
import com.hexotic.v2.gui.theme.Theme;

public class ReleaseNotes extends JPanel {

	private static final long serialVersionUID = 4376712195634925368L;

	private JPanel notesContainer;
	private Image icon;

	public ReleaseNotes() {
		this.setBackground(Theme.CONTROL_BAR_BACKGROUND);
		this.setPreferredSize(new Dimension(600, 400));
		this.setLayout(new FlowLayout());
		
		notesContainer = new JPanel(new AnimatedGridLayout(true));
		notesContainer.setBackground(Theme.CONTROL_BAR_BACKGROUND);

		JScrollPane scroller = new JScrollPane(notesContainer);
		scroller.setPreferredSize(new Dimension(550, 320));
		scroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.getVerticalScrollBar().setUI(new SimpleScroller());
		scroller.getVerticalScrollBar().setPreferredSize(new Dimension(5, 5));
		scroller.getVerticalScrollBar().setUnitIncrement(25);

		try {
			importNotes();
		} catch (InvalidFileFormatException e) {
			Log.getInstance().error(this, "Failed to import release notes", e);
		} catch (FileNotFoundException e) {
			Log.getInstance().error(this, "Failed to import release notes", e);
		} catch (IOException e) {
			Log.getInstance().error(this, "Failed to import release notes", e);
		}

		JLabel title = new JLabel("Release Notes");
		title.setFont(Theme.CONTROL_BAR_FONT.deriveFont(18.0f));
		title.setPreferredSize(new Dimension(550,25));
		
		SoftButton closeButton = new SoftButton("Okay, I'm done here");
		closeButton.setBackgroundColor(Theme.DARK);
		closeButton.setFont(Theme.CONTROL_BAR_FONT);
		closeButton.setPreferredSize(new Dimension(140,24));
		closeButton.setArc(4);
		closeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PopupFactory.getPopupWindow().propagateClose();
			}
		});
		
		this.add(title);
		this.add(scroller);
		this.add(closeButton);
		icon = Resources.getInstance().getImage("support/bullet.png");
	}

	private void importNotes() throws InvalidFileFormatException, FileNotFoundException, IOException {
		Ini ini = new Ini(Resources.getInstance().getDoc("releaseNotes.ini"));
		int id = 0;
		for (String version : ini.keySet()) {
			Section section = ini.get(version);

			String name = section.get("name").replace("\"", "");
			String date = section.get("date").replace("\"", "");
			boolean milestone = section.get("milestone").equalsIgnoreCase("true");
			ReleaseNote note = new ReleaseNote(id++, name, version, date, milestone);

			for (String optionKey : section.keySet()) {
				if (optionKey.startsWith("add")) {
					note.addNote("+", section.get(optionKey).replace("\"", ""));
				} else if (optionKey.startsWith("rem")) {
					note.addNote("-", section.get(optionKey).replace("\"", ""));
				}
			}
			notesContainer.add(note);
			if(id == 1){
				note.expand();
			}
		}

	}

	class ReleaseNote extends JPanel implements Comparable<ReleaseNote> {
		private JXCollapsiblePane infoPane;
		private JEditorPane editor;
		private int id = 0;

		public ReleaseNote(int id, String title, String version, String date, boolean milestone) {
			this.id = id;
			this.setLayout(new BorderLayout());
			ReleaseNoteTitle noteTitle = new ReleaseNoteTitle(version, title, date, milestone);
			noteTitle.addMouseListener(new MouseListener() {
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
			contentPanel.setPreferredSize(new Dimension(520, 150));
			contentPanel.setBackground(Theme.CONTROL_BAR_BACKGROUND);
			editor = new JEditorPane();
			editor.setEditable(false);
			editor.setBackground(Theme.CONTROL_BAR_BACKGROUND);
			editor.setFont(Theme.SWITCH_FONT);
			editor.setSelectionColor(Theme.MAIN_COLOR_FOUR);;

			JScrollPane editorScroller = new JScrollPane(editor);
			editorScroller.setBorder(BorderFactory.createLineBorder(Theme.CONTROL_BAR_BORDER));
			editorScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			editorScroller.getVerticalScrollBar().setUI(new SimpleScroller());
			editorScroller.getVerticalScrollBar().setPreferredSize(new Dimension(5, 5));
			editorScroller.getVerticalScrollBar().setUnitIncrement(25);
			editorScroller.setBackground(Theme.CONTROL_BAR_BACKGROUND);
			contentPanel.add(editorScroller);

			infoPane.setCollapsed(true);
			infoPane.setAnimated(true);
			infoPane.setContentPane(contentPanel);

			this.add(infoPane, BorderLayout.CENTER);

		}

		public void expand() {
			infoPane.setCollapsed(false);
		}
		
		public void addNote(String prefix, String note) {
			editor.setText(editor.getText() + prefix + " " + note + "\n");
		}

		public int getId() {
			return this.id;
		}

		@Override
		public int compareTo(ReleaseNote note) {
			return getId() - note.getId();
		}
	}

	class ReleaseNoteTitle extends JPanel {
		private String version;
		private String name;
		private String date;
		private boolean milestone;

		public ReleaseNoteTitle(String version, String name, String date, boolean milestone) {
			this.version = version;
			this.name = name;
			this.date = date;
			this.milestone = milestone;
			this.setPreferredSize(new Dimension(30, 30));
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

			if (milestone) {
				gp1 = new GradientPaint(getWidth(), getWidth(), Theme.MAIN_COLOR_THREE, 0, getWidth(), Theme.MAIN_COLOR_FOUR, true);
			}
			g2d.setPaint(gp1);
			g2d.fillRoundRect(0, 0, getWidth(), getHeight() + 10, 5, 5);

			g2d.drawImage(icon, 2, -2, null);

			g2d.setPaint(Theme.CONTROL_BAR_BACKGROUND);
			g2d.setFont(Theme.CONTROL_BAR_FONT.deriveFont(14.0f));
			g2d.drawString(name + " (" + version + ")", 30, 20);

			g2d.setFont(Theme.CONTROL_BAR_FONT.deriveFont(11.0f));
			g2d.drawString(date, getWidth() - 70, 25);

		}
	}

}
