package com.hexotic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdesktop.swingx.prompt.PromptSupport;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.hexotic.cons.Constants;
import com.hexotic.utils.CentralDownloadControl;
import com.hexotic.utils.XButton;
import com.hexotic.utils.XTextField;

public class UrlPanel extends JPanel implements FlavorListener, ClipboardOwner, Observer{
    private Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  

	private JLabel urlLabel = new JLabel("URL:");
	private XTextField url;
	private XButton downloadButton = new XButton("Download");
	private String clipboardCache = "";
	
	public UrlPanel(){
		this.setBackground(Color.WHITE);
		Font font = new Font("Arial", Font.BOLD, 11);
		urlLabel.setFont(font);
		BorderLayout layout = new BorderLayout();
		layout.setHgap(10);
		this.setLayout(layout);
		this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		url = new XTextField();
		url.setFont(font);
		PromptSupport.init("Paste Video URL (ctrl+v) or just tell me what to find and I'll take care of it", Color.GRAY, Color.WHITE, url);
		url.setPreferredSize(new Dimension(400, 20));
		this.add(urlLabel, BorderLayout.WEST);
		this.add(url, BorderLayout.CENTER);
		url.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER){
					addDownload();
				}
			}
			public void keyReleased(KeyEvent e) {
			}
			public void keyTyped(KeyEvent e) {
			}
		});
		
		
		this.add(downloadButton, BorderLayout.EAST);
		downloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addDownload();
			}
		});		
		
		CentralDownloadControl.getInstance().addObserver(this);
	}
	

	
	public void addDownload(){
		String inputUrl = url.getText();
		if(inputUrl.equals("[update]")){
			try {
				new MessageBox(Constants.MSG_1, "Manual Update","Attempting to manually update core", CentralDownloadControl.getInstance().updateDownloader());
			} catch (IOException e) {
				new MessageBox(Constants.MSG_2, "Update Failed", "There was a problem updating core components", e.toString());
			}
		}else if(isYoutubePlaylist(inputUrl)){
			try{
				parseYoutubePlaylist(inputUrl);
			} catch (Exception e) {
				new MessageBox(Constants.MSG_2, "Couldn't Download", "There was a problem reading playlist contents", e.toString());
			}
		}else if((!inputUrl.equals("")) && (!inputUrl.equals(" "))){
			CentralDownloadControl.getInstance().addDownload(inputUrl);
		}
		url.setText("");
	}

	public void parseYoutubePlaylist(String playlistUrl) throws Exception{
		String playlistToken = extractYoutubePlaylistID(playlistUrl);
		String[] playlists = new String[4]; 
		if(playlistToken.equals("")){
			throw new Exception("Couldn't Extract Playlist Token");
		}
		playlists[0] = "https://gdata.youtube.com/feeds/api/playlists/"+playlistToken+"?v=1&start-index=1&max-results=50";
		playlists[1] = "https://gdata.youtube.com/feeds/api/playlists/"+playlistToken+"?v=1&start-index=51&max-results=50";
		playlists[2] = "https://gdata.youtube.com/feeds/api/playlists/"+playlistToken+"?v=1&start-index=101&max-results=50";
		playlists[3] = "https://gdata.youtube.com/feeds/api/playlists/"+playlistToken+"?v=1&start-index=151&max-results=50";
		
		for(String playlist : playlists){
			URL url = new URL(playlist);
			URLConnection connection = url.openConnection();
			Document doc = parseXML(connection.getInputStream());
			NodeList descNodes = doc.getElementsByTagName("link");
			for(int i = 0; i < descNodes.getLength(); i++){
				String videoUrl = descNodes.item(i).getAttributes().getNamedItem("href").toString();
				videoUrl = videoUrl.replace("href=", "").replace("\"", "");
				if(CentralDownloadControl.getInstance().isValid(videoUrl)){
					CentralDownloadControl.getInstance().addDownload(videoUrl);
				}
			}
		}
		
	}
	
	private boolean isYoutubePlaylist(String url){
		if(url.contains("youtube.com") && (url.contains("playlist?list=") || url.contains("&list="))){
			return true;
		}
		return false;
	}
	
	private String extractYoutubePlaylistID(String url){
		if(url.contains("&list=")){
			String[] elements = url.split("=");
			if(elements == null || elements.length < 3){
				return "";
			}
			return elements[2];
		}
		String[] elements = url.split("=");
		if(elements == null || elements.length < 2){
			return "";
		}
		return elements[1];
	}
	
    private Document parseXML(InputStream stream) throws Exception {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            
            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            throw ex;
        }       

        return doc;
    }
	
	@Override
	public void flavorsChanged(FlavorEvent e) {
		String data = "";
		try {
			data = (String) clip.getData(DataFlavor.stringFlavor);
			if(CentralDownloadControl.getInstance().isValid(data) && !data.equals(clipboardCache)){
				System.out.println(data);
				CentralDownloadControl.getInstance().addDownload(data);
				StringSelection emptyString = new StringSelection("");
				clip.setContents(emptyString, this);
				clipboardCache = data;
			}else{
				StringSelection emptyString = new StringSelection(data);
				clip.setContents(emptyString, this);				
			}
		} catch (Exception ex){
			new MessageBox(Constants.MSG_5, "That's weird", "I couldn't use your clipboard",e.toString());	
		}
	}
	
	@Override
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
	}
	
	
	private void setupClipboard(){
		clip.setContents(clip.getContents(null), this);
        clip.addFlavorListener(this);
        System.out.println("Clipboard Turned on");
	}

	private void unsetClipboard(){
		clip.removeFlavorListener(this);
		System.out.println("Clipboard Turned off");
	}

	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			if(((String)arg).equals("MENU")){
				if(CentralDownloadControl.getInstance().downloadOnCopy()){
					setupClipboard();
				}else{
					unsetClipboard();
				}
			}
		}
	}
}
