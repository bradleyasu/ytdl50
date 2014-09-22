package com.hexotic.v2.console;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.hexotic.lib.resource.Resources;
import com.hexotic.lib.ui.panels.SimpleScroller;
import com.hexotic.v2.gui.theme.Theme;

/**
 * This is an added "hidden" feature that will allow log viewing in real-time
 * via a built-in console window
 * 
 * @author Bradley
 * 
 */
public class Console extends JInternalFrame {

	private static final long serialVersionUID = -283865083441921493L;
	private ConsoleContent output;
	private JScrollPane scroll;
	
	public Console() {
		super("Hexotic Software - Console", false, // resizable
				false, // closable
				true, // maximizable
				false);// iconifiable

		// Default Size
		setSize(600, 250);
		this.setOpaque(true);
		// pssh, who needs borders
		// this.setBorder(BorderFactory.createEmptyBorder());
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 240)));

		((BasicInternalFrameUI) this.getUI()).setNorthPane(null);

		// Add a scroll bar to the console window
		output = new ConsoleContent();
		scroll = new JScrollPane(output);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUI(new SimpleScroller());
		scroll.setBackground(new Color(0, 0, 0, 0));
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(5,5));
		// Add the console "editor" to the window
		this.add(scroll);

	}

	public synchronized void println(String line) {
		try {
			Document doc = output.getDocument();
			doc.insertString(doc.getLength(), line + "\n", null);
			output.setCaretPosition(doc.getLength());
		} catch (BadLocationException exc) {
			// Nothing to worry about really
		}
	}
	
	private class ConsoleContent extends JEditorPane {
		private static final long serialVersionUID = -8670826262403444979L;

		public ConsoleContent() {
			this.setBackground(new Color(0, 0, 0, 255));
			this.setFont(new Font("Courier New", Font.BOLD, 12));
			this.setForeground(Theme.MAIN_COLOR_TWO);
			this.setEditable(false);
		}
	}
}