package com.hexotic.utils;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ImagePanel extends JPanel {

    private final Image image;

    public ImagePanel(Image image) {
        super();
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) throws MalformedURLException {
        final URL url = new URL("http://pscode.org/media/starzoom-thumb.gif");
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame f = new JFrame("Image");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setLocationByPlatform(true);

                Image image = f.getToolkit().createImage(url);
                ImagePanel imagePanel = new ImagePanel(image);
                imagePanel.setLayout(new GridLayout(5, 10, 10, 10));
                imagePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
                for (int ii = 1; ii < 51; ii++) {
                    imagePanel.add(new JButton("" + ii));
                }

                f.setContentPane(imagePanel);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}