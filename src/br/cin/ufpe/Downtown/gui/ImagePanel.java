package br.cin.ufpe.Downtown.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private Image img;

	public ImagePanel(String source, int w, int h){

		this.img = new ImageIcon(Main.class.getResource(source)).getImage();
		setPreferredSize(new Dimension(w,h));
		setMinimumSize(new Dimension(w,h));
		setMaximumSize(new Dimension(w,h));
		setSize(new Dimension(w,h));
		setLayout(null);

	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}

