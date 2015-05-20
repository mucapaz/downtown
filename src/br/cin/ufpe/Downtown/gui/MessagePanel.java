package br.cin.ufpe.Downtown.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class MessagePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MessagePanel(String username, String msg, boolean myself) {
		setLayout(null);
		setMinimumSize(new Dimension(690, 60));
		setMaximumSize(new Dimension(690, 60));
		setPreferredSize(new Dimension(690, 60));
		
		JLabel name = new JLabel(username);
		name.setForeground(new Color(65,105,158));
		name.setFont(new Font("Arial", Font.BOLD, 13));
		if(myself){
			name.setForeground(new Color(79,79,79));
			name.setFont(new Font("Arial", Font.PLAIN, 13));
		}
		name.setVerticalAlignment(SwingConstants.TOP);
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		
		name.setBounds(10, 11, 111, 36);
		add(name);
		
		JLabel message = new JLabel("Mensagem");
		message.setForeground(new Color(66,66,66));
		message.setVerticalAlignment(SwingConstants.TOP);
		message.setHorizontalAlignment(SwingConstants.LEFT);
		message.setText("<html>" + msg + "</html>");
		message.setBounds(152, 11, 417, 36);
		add(message);

	}

}
