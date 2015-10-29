package com.hexotic.v2.gui.primary;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.hexotic.lib.ui.layout.AnimatedGridLayout;
import com.hexotic.v2.gui.primary.downloaditem.Item;
import com.hexotic.v2.gui.theme.Theme;

public class DownloadContainer extends JPanel{

	private static final long serialVersionUID = -4992926983295550736L;
	private ExecutorService es = Executors.newFixedThreadPool(10);
	private int counter = 0;
	public DownloadContainer() {
		this.setBackground(Theme.MAIN_BACKGROUND);
		this.setLayout(new AnimatedGridLayout(true));

	}
	
	public void addDownload(String url){
		Item item = new Item(url, counter++);
		this.add(item);
		es.execute(item);
	}
	
	/**
	 * Adds all videos of a youtube playlist to the download queue
	 * 
	 * @param playlistUrl
	 * 					Youtube playlist url
	 * 
	 * @throws Exception
	 */
	public void addPlaylist(String playlistUrl) throws Exception {
		String playlistToken = extractYoutubePlaylistID(playlistUrl);
		String[] playlists = new String[4];
		if (playlistToken.equals("")) {
			throw new Exception("Couldn't Extract Playlist Token");
		}
		playlists[0] = "https://gdata.youtube.com/feeds/api/playlists/" + playlistToken + "?v=1&start-index=1&max-results=50";
		playlists[1] = "https://gdata.youtube.com/feeds/api/playlists/" + playlistToken + "?v=1&start-index=51&max-results=50";
		playlists[2] = "https://gdata.youtube.com/feeds/api/playlists/" + playlistToken + "?v=1&start-index=101&max-results=50";
		playlists[3] = "https://gdata.youtube.com/feeds/api/playlists/" + playlistToken + "?v=1&start-index=151&max-results=50";

		for (String playlist : playlists) {
			URL url = new URL(playlist);
			URLConnection connection = url.openConnection();
			Document doc = parseXML(connection.getInputStream());
			NodeList descNodes = doc.getElementsByTagName("link");
			for (int i = 0; i < descNodes.getLength(); i++) {
				String videoUrl = descNodes.item(i).getAttributes().getNamedItem("href").toString();
				videoUrl = videoUrl.replace("href=", "").replace("\"", "");
				/* Only download the video if the url is a youtube video url (there are different urls throughout the page */
				if(videoUrl.contains("youtube.com/watch?v=")){
					addDownload(videoUrl);
				}
			}
		}

	}

	/**
	 * Check to see if the url is a YT Playlist url
	 * 
	 * @param url 
	 * 			URL to check
	 * @return True if it's a playlist, false otherwise
	 */
	public boolean isYoutubePlaylist(String url) {
		if (url.contains("youtube.com") && (url.contains("playlist?list=") || url.contains("&list="))) {
			return true;
		}
		return false;
	}

	/**
	 * The the playlist ID out of the url
	 * 
	 * @param url
	 * 			Playlist URL
	 * @return
	 * 		  YT Playlist URL ID
	 */
	private String extractYoutubePlaylistID(String url) {
		if (url.contains("&list=")) {
			String[] elements = url.split("=");
			if (elements == null || elements.length < 3) {
				return "";
			}
			return elements[2];
		}
		String[] elements = url.split("=");
		if (elements == null || elements.length < 2) {
			return "";
		}
		return elements[1];
	}

	/**
	 * Parse the XML document returned from gdata
	 * 
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	private Document parseXML(InputStream stream) throws Exception {
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try {
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);
		} catch (Exception ex) {
			throw ex;
		}

		return doc;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Theme.MAIN_COLOR_FOUR);
		g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);

		
	}
}
