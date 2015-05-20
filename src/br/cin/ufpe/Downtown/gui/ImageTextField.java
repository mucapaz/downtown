package br.cin.ufpe.Downtown.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class ImageTextField extends JTextField{
	
	private Image img;

	public ImageTextField(String source){

		this.img = new ImageIcon(Main.class.getResource(source)).getImage();

	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
