package br.cin.ufpe.Downtown.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class UserListItem extends JPanel{
	
	private Chat chat;
	
	private UserListEntry user;
	
	public UserListItem(final UserListEntry user, final Chat chat){
		this.chat = chat;
		this.user = user;
		this.setPreferredSize(new Dimension(226,60));
		this.setMinimumSize(new Dimension(226,60));
		this.setMaximumSize(new Dimension(226,60));
		this.setSize(new Dimension(226,60));
		this.setLayout(null);
		this.setBackground(new Color(0,0,0, 0));
		
		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent evt) {
				//Logger.log("Clicked other button");
				try {
					chat.connectToClient(user.getIp(), user.getPort());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void mouseEntered(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
				setCursor(cursor);
			}

			public void mouseExited(MouseEvent e) {
				Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR); 
				setCursor(cursor);
			}
		});
		
		JLabel lbl = new JLabel(user.getUser().getName());
		lbl.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl.setForeground(new Color(196,196,196));
		lbl.setBounds(62, 22, 148, 14);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 59, 226, 1);
		separator.setForeground(new Color(43,43,43));
		this.add(separator);
		
		this.add(lbl);
	}

}
