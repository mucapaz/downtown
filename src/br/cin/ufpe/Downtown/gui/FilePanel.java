package br.cin.ufpe.Downtown.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import br.cin.ufpe.Downtown.application.entities.FileWrapper;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Pattern;

public class FilePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public FilePanel(String username, final FileWrapper file, boolean myself) {
		setLayout(null);
		setMinimumSize(new Dimension(690, 60));
		setPreferredSize(new Dimension(690, 60));
		setMaximumSize(new Dimension(690, 60));
		
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
		
		JButton download = new JButton("Download file " + file.getFileName());
		download.setBounds(152, 11, 417, 36);
		add(download);
		
		final JFileChooser fileDialog = new JFileChooser();

		download.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
            	int openChoice = fileDialog.showSaveDialog(null);
            	if (openChoice == JFileChooser.APPROVE_OPTION) {
            			String path = fileDialog.getSelectedFile().getAbsolutePath();
            			try {
							file.toFile(path+"/"+file.getFileName());
						} catch (IOException e1) {
							 JOptionPane.showMessageDialog(null, "Can't save file, sorry :(", "Error", JOptionPane.ERROR_MESSAGE);
							e1.printStackTrace();
						}
            	}
			}
		});
		
		

	}

}
