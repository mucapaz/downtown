package br.cin.ufpe.Downtown.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

public class EmoticonPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public EmoticonPanel(String username, int emo, boolean myself) {
		setLayout(null);
		setMinimumSize(new Dimension(690, 70));
		setPreferredSize(new Dimension(690, 70));
		setMaximumSize(new Dimension(690, 70));
		
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
		
		
		JLabel emoticon = new JLabel(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/"+ emo +".jpg")));
		emoticon.setBounds(152, 11, 50, 59);
		add(emoticon);
	

	}

}
