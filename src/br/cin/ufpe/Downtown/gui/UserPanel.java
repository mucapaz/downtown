package br.cin.ufpe.Downtown.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.cin.ufpe.Downtown.application.entities.UserListEntry;

public class UserPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private UserListEntry user;
	
	public UserListEntry getUser() {
		return user;
	}

	public void setUser(UserListEntry user) {
		this.user = user;
	}

	public UserPanel(UserListEntry user) {
		this.user = user;
		setLayout(null);
		setOpaque(false);
		
		setMinimumSize(new Dimension(259, 60));
		setPreferredSize(new Dimension(259, 60));
		setMaximumSize(new Dimension(259, 53));
		
		ImageIcon imageIcon = new ImageIcon(user.getUser().getImage().getFileByte()); // load the image to a imageIcon
		Image imagex = imageIcon.getImage(); // transform it 
		Image newimg = imagex.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		JLabel image = new JLabel(imageIcon);
		image.setBounds(5,11, 30, 30);
		add(image);
		
		
		JLabel username = new JLabel(user.getUser().getName());
		username.setBounds(40, 10, 242, 32);
		username.setForeground(new Color(219,219,219));
		add(username);

	}

}
