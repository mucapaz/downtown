package br.cin.ufpe.Downtown.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSeparator;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.P2PConnection;
import br.cin.ufpe.Downtown.application.entities.FileWrapper;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;

public class Main {

	private JFrame frmDowntownChat;
	
	private JTextField nameField;
	
	private JTextField serverField;
	
	private JLabel lblName;
	
	private Chat chat;
	
	private ChatPanel chatPanel;
	
	private JPanel main_panel;
	
	private JLabel lblServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		frmDowntownChat.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDowntownChat = new JFrame();
		frmDowntownChat.setResizable(false);
		frmDowntownChat.setTitle("Downtown - Chat & Pub");
		frmDowntownChat.setBounds(0, 0, 1003, 621);
		frmDowntownChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDowntownChat.getContentPane().setLayout(null);
		frmDowntownChat.setLocationRelativeTo(null);
		
		main_panel = new ImagePanel("/br/cin/ufpe/Downtown/gui/assets/main_bg.jpg", 1000, 600);
		main_panel.setBounds(0, 0, 1000, 595);
		frmDowntownChat.getContentPane().add(main_panel);
		main_panel.setLayout(null);
		
		
		JLabel nameFieldWrapper = new JLabel(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/main_input.png")));
		nameFieldWrapper.setBounds(381, 317, 246, 44);
		nameFieldWrapper.setLayout( new BorderLayout() );
		main_panel.add(nameFieldWrapper);
		
		JLabel serverFieldWrapper = new JLabel(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/main_input.png")));
		serverFieldWrapper.setBounds(381, 410, 246, 44);
		serverFieldWrapper.setLayout( new BorderLayout() );
		main_panel.add(serverFieldWrapper);
		
		lblName = new JLabel("Your Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Alegreya", Font.BOLD, 16));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(381, 295, 246, 14);
		main_panel.add(lblName);
		
		lblServer = new JLabel("Server IP and Port");
		lblServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblServer.setFont(new Font("Alegreya", Font.BOLD, 16));
		lblServer.setForeground(Color.WHITE);
		lblServer.setBounds(381, 390, 246, 14);
		main_panel.add(lblServer);
		
		final JFileChooser fileDialog = new JFileChooser();
		

		JButton enterButton = new JButton(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/main_bt.jpg")));
		enterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!(Pattern.matches("^[a-zA-Z]+$", nameField.getText()))) {
				    JOptionPane.showMessageDialog(null, "Apenas letras e espaços são permitidos", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					
					try {
						fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		            	int openChoice = fileDialog.showOpenDialog(null);
		            	if (openChoice == JFileChooser.APPROVE_OPTION) {
		            		
		            		String path = fileDialog.getSelectedFile().getAbsolutePath();

							FileWrapper image = FileWrapper.fromFile(path.substring(0,path.length() - fileDialog.getSelectedFile().getName().length()), fileDialog.getSelectedFile().getName());
							
							initChat(image);
		            	}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
				
			}
		});
		
		enterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enterButton.setBorder(BorderFactory.createEmptyBorder());
		enterButton.setContentAreaFilled(false);
		enterButton.setBounds(480, 480, 116, 43);
		main_panel.add(enterButton);
		
		nameField = new JTextField(10);
		nameFieldWrapper.add(nameField);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		nameField.setOpaque( false );
		nameField.setBounds(381, 347, 246, 44);
		nameField.setForeground(Color.WHITE);
		nameField.setText("Suruagy");
		nameField.setBorder(BorderFactory.createEmptyBorder());
		nameFieldWrapper.setPreferredSize(new Dimension(nameFieldWrapper.getPreferredSize().width,nameField.getPreferredSize().height));
		
		
		serverField = new JTextField(10);
		serverFieldWrapper.add(serverField);
		serverField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		serverField.setHorizontalAlignment(SwingConstants.CENTER);
		serverField.setOpaque( false );
		serverField.setBounds(381, 347, 246, 44);
		serverField.setForeground(Color.WHITE);
		serverField.setText("127.0.0.1:30000");
		serverField.setBorder(BorderFactory.createEmptyBorder());
		serverFieldWrapper.setPreferredSize(new Dimension(serverFieldWrapper.getPreferredSize().width,serverField.getPreferredSize().height));
		
		
	}

	protected void initChat(FileWrapper image) throws IOException {
		
		User user = new User(nameField.getText(), 'M', image);
		
		this.chat = new Chat(serverField.getText().split(":")[0], Integer.parseInt(serverField.getText().split(":")[1]), user, this);
		
		this.chatPanel = new ChatPanel(user, this.chat);
		chatPanel.setBounds(0,0,1000,600);
		
		main_panel.setVisible(false);
		frmDowntownChat.getContentPane().add(chatPanel);
		//frmDowntownChat.getContentPane().reva
	}

	public void updateUserList(Vector<UserListEntry> onlineUsers) {
		if(this.chatPanel != null){
			this.chatPanel.updateUserList(onlineUsers);
		}
		
	}

	public void updateGUIMessage(P2PConnection connection, Message message) {
		this.chatPanel.updateMessage(connection, message);
		
	}

	public void updateGUIPanel(P2PConnection p2pConnection) {
		this.chatPanel.updatePanel(p2pConnection);
		
	}

	public void updateGUIFile(P2PConnection connection, Message message) {
		this.chatPanel.updateFile(connection, message);
		
	}

	public void updateGUIEmoticon(P2PConnection connection, Message message) {
		this.chatPanel.updateEmoticon(connection, message);
		
	}

	public void updateGUIAccepted(P2PConnection connection, Message message) {
		this.chatPanel.updateAccepted(connection, message);
		
	}

	public void updateGUIProfile(P2PConnection connection, Message message) {
		this.chatPanel.updateProfile(connection, message);
		
	}
}
