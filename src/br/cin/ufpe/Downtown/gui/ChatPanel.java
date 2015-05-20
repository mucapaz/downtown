package br.cin.ufpe.Downtown.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ScrollPaneConstants;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.P2PConnection;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;
import br.cin.ufpe.Downtown.application.utils.Logger;

import javax.swing.BoxLayout;

public class ChatPanel extends JPanel {
	
	private User user;
	private Chat chat;
	private JPanel userList;
	private JPanel userPanel;
	private JPanel panelHolder;
	
	boolean searching = false;
	
	private Vector<ConversationPanel> conversations;
	private JPanel welcHolder;
	
	/**
	 * Create the panel.
	 */
	public ChatPanel(User user, final Chat chat) {
		
		conversations = new Vector<ConversationPanel>();
		
		this.chat = chat;
		
		this.user = user;
		
		setPreferredSize(new Dimension(1000,600));
		setMinimumSize(new Dimension(1000,600));
		setMaximumSize(new Dimension(1000,600));
		setSize(new Dimension(1000,600));
		setBounds(0, 0, 1000, 595);
		setLayout(null);
		
		JButton closeButton = new JButton(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/close_bt.jpg")));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		closeButton.setBorder(BorderFactory.createEmptyBorder());
		closeButton.setContentAreaFilled(false);
		closeButton.setBounds(946,18, 31, 31);
		add(closeButton);
		
		JLabel lblNewLabel = new JLabel(user.getName());
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		lblNewLabel.setForeground(new Color(196,196,196));
		lblNewLabel.setBounds(66, 25, 148, 20);
		add(lblNewLabel);
		
		ImageIcon imageIcon = new ImageIcon(user.getImage().getFileByte()); // load the image to a imageIcon
		Image imagex = imageIcon.getImage(); // transform it 
		Image newimg = imagex.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		
		JLabel image = new JLabel(imageIcon);
		image.setBounds(15,15, 40, 40);
		add(image);
		
		JLabel searchFieldWrapper = new JLabel(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/search.jpg")));
		searchFieldWrapper.setBounds(15, 124, 229, 31);
		searchFieldWrapper.setLayout( new BorderLayout() );
		add(searchFieldWrapper);
		
		final JTextField searchField = new PlaceholderTextField("Pesquisar");
		searchFieldWrapper.add(searchField);
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchField.setOpaque( false );
		searchField.setBounds(15, 124, 229, 31);
		searchField.setForeground(new Color(54,54,54));
		searchField.setBorder(BorderFactory.createEmptyBorder());
		searchField.setBorder(BorderFactory.createCompoundBorder(
				searchField.getBorder(), 
		        BorderFactory.createEmptyBorder(0, 30, 0, 0)));
		searchField.setPreferredSize(new Dimension(searchFieldWrapper.getPreferredSize().width,searchField.getPreferredSize().height));
		
		searchField.addKeyListener(new KeyListener(){
		    @Override
		    public void keyPressed(KeyEvent e){

		        
		    }

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				userPanel.removeAll();
		        if(searchField.getText().equalsIgnoreCase("")){
		        	searching = false;
		        	for(UserListEntry user: chat.getOnlineUsers()){
						addUser(user);
					}
		        }else{
		        	searching = true;
		        	for(UserListEntry user: chat.getOnlineUsers()){
		        		if(searchField.getText().equalsIgnoreCase(user.getUser().getName().substring(0, searchField.getText().length()))){
		        			addUser(user);
		        		}
					}
		        	
		        }
		        userPanel.revalidate();

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});
		
		// User list 
		this.userList = new JPanel();
		userList.setBounds(15, 174, 226, 372);
		add(userList);
		userList.setOpaque(false);
		userList.setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		userList.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		userPanel = new JPanel();
		userPanel.setOpaque(false);
		scrollPane.setViewportView(userPanel);
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		
		this.updateUserList(null);
		
		welcHolder = new JPanel();
		welcHolder.setOpaque(false);
		welcHolder.setLayout(null);
		welcHolder.setBackground(new Color(255,0,0));
		welcHolder.setBounds(260,70, 740, 530);
		welcHolder.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(welcHolder);
		welcHolder.add(new ImagePanel("/br/cin/ufpe/Downtown/gui/assets/main.jpg", 740,530));
		
		panelHolder = new JPanel();
		panelHolder.setOpaque(false);
		panelHolder.setLayout(null);
		panelHolder.setBackground(new Color(255,0,0));
		panelHolder.setBounds(260,70, 740, 530);
		panelHolder.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(panelHolder); 
		
				
		//JPanel conv = new ConversationPanel();
		//panelHolder.add(conv);
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/chat_bg.jpg")).getImage(), 0, 0, null);
	}
	
	
	public void updateMessage(P2PConnection connection, Message message){
		for(ConversationPanel c: this.conversations){
			if(c.getConnection().getPort() == connection.getPort()){
				c.addMessage(message);
			}
		}
	}
	
	public void updateUserList(Vector<UserListEntry> onlineUsers){
		userPanel.removeAll();
		if(onlineUsers != null && !searching){
			Logger.log("Updating GUI user list", this);
			for(UserListEntry user: onlineUsers){
				addUser(user);
			}
			
			for(ConversationPanel panel : this.conversations){
				boolean f = false;
				for(UserListEntry user: onlineUsers){
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
					System.out.println(panel.getConnection().getPort() +"  ---  "+user.getPort());
					if(panel.getConnection().getPort() == user.getPort()){
						f = true;
						break;
					}
				}
				
				if(!f){
					System.out.println("@@@@@@@@@@ HIDDING");
					if(panel.isVisible()){
						JOptionPane.showMessageDialog(null,
							    panel.getConnection().getUser().getName() + " disconnected :(",
							    "Error",
							    JOptionPane.ERROR_MESSAGE);
							panel.setVisible(false);
							panelHolder.revalidate();
							panelHolder.repaint();
					}
					
				}
			}

			
		}
		
		userPanel.revalidate();
		userPanel.repaint();
	}

	
	public void addUser(UserListEntry user){
		final UserPanel usr = new UserPanel(user);
		
		usr.addMouseListener(new MouseAdapter(){  
	          
		      public void mouseClicked(MouseEvent e){  
		         try {
					Logger.log("ChatPanel > Clicked on user button", this);
					
		        	User toFind = new User("Default", 'M');
					toFind.setPort(usr.getUser().getPort());
					P2PConnection connection = null;
					
					// Find the connection
					Vector<P2PConnection> vecP2PConn = chat.getP2pConnections();
					
					for(int i=0; i < vecP2PConn.size(); i++){
						P2PConnection p2pConn = vecP2PConn.get(i);
						if(p2pConn.getPort() == usr.getUser().getPort() && p2pConn.isAuthenticated()) {
							Logger.log("ChatPanel > Found connection to user", this);
							connection = p2pConn;
							break;
						}
					}
		        	 
					// If not already connected then connect
					if(connection == null){
						Logger.log("ChatPanel > User not connected, connecting", this);
						chat.connectToClient(usr.getUser().getIp(), usr.getUser().getPort());
						connection = chat.findP2PConnection(toFind);
					}else if(!connection.isAuthorized()){
						connection.getToClientMessages().add(new Message("AUTHORIZE_CONNECTION", chat.getPort()));
					}
					

		        	// Try to find the panel and hide all panels
					ConversationPanel conv = null;
					for(ConversationPanel p: conversations){
						p.setVisible(false);
						if(p.getConnection().getPort() == usr.getUser().getPort() && p.getConnection().isAuthorized()){
							Logger.log("ChatPanel > Found user panel", this);
							conv = p;
						}
					}
						
					// If the panel doesnt exists, then create it (but hide it)
					if(conv == null){
						Logger.log("ChatPanel > Panel doesn't not exists, creating one", this);
						conv = new ConversationPanel(connection,chat);
						conv.setVisible(false);
						panelHolder.add(conv);
						conversations.add(conv);
					}else if(conv.getConnection().isAuthorized()){
						Logger.log("ChatPanel > Panel already exists",  this);
						conv.setVisible(true);
					}

					panelHolder.revalidate();
					panelHolder.repaint();
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,
						    "I'm sorry, we couldn't connect to the user :(",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
		    	  
		       };  
		       
		       public void mouseEntered(MouseEvent e) {
		    	   Cursor cursor = Cursor.getDefaultCursor();
		    	   	usr.setOpaque(true);
		    	   	usr.setBackground(new Color(46,46,46));
		    	     //change cursor appearance to HAND_CURSOR when the mouse pointed on images
		    	     cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
		    	     setCursor(cursor);
		    	  }
		       
		       public void mouseExited(MouseEvent e) {
		    	   Cursor cursor = Cursor.getDefaultCursor();
		    	   usr.setOpaque(false);
		    	     //change cursor appearance to HAND_CURSOR when the mouse pointed on images
		    	     cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR); 
		    	     setCursor(cursor);
		    	  }
		   
		});  
		
		userPanel.add(usr);	
	}
	
	public void updatePanel(P2PConnection p2pConnection) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> OPENING PANEL");
		int dialogResult = JOptionPane.showConfirmDialog (null, "Would you like to connect to " + p2pConnection.getUser().getName() +"?","Hey",JOptionPane.YES_NO_OPTION);
		if(dialogResult == JOptionPane.YES_OPTION){
			//welcHolder.setVisible(false);
			//p2pConnection.setAuthenticated(true);
			p2pConnection.setAuthorized(true);
			ConversationPanel conv = new ConversationPanel(p2pConnection, this.chat);
			conv.setVisible(true);
			p2pConnection.getToClientMessages().add(new Message("ACCEPT_CONNECTION", chat.getPort()));
			panelHolder.add(conv);
			conversations.add(conv);
			welcHolder.setVisible(false);
			welcHolder.revalidate();
			welcHolder.repaint();
			panelHolder.revalidate();
			panelHolder.repaint();
		}else{
			p2pConnection.setAuthorized(false);
			p2pConnection.getToClientMessages().add(new Message("DECLINE_CONNECTION", chat.getPort()));
		}
		
	}

	public void updateFile(P2PConnection connection, Message message) {
		for(ConversationPanel c: this.conversations){
			if(c.getConnection().getPort() == connection.getPort()){
				c.addFile(message);
			}
		}
		
	}

	public void updateEmoticon(P2PConnection connection, Message message) {
		for(ConversationPanel c: this.conversations){
			if(c.getConnection().getPort() == connection.getPort()){
				c.addEmoticon(message);
			}
		}
	}

	public void updateAccepted(P2PConnection connection, Message message) {
		for(ConversationPanel c: this.conversations){
			if(c.getConnection().getPort() == (int) message.getData()){
				c.setVisible(true);
				welcHolder.setVisible(false);
				welcHolder.revalidate();
				welcHolder.repaint();
				panelHolder.revalidate();
				panelHolder.repaint();
				
			}
		}
	}

	public void updateProfile(P2PConnection connection, Message message) {
		
		String str = "<html><body><h1>" + connection.getUser().getName() + "</h1>"
				+ "----------------------- <br>"
				+ " > Talking to: <br>";
		
		for(String s: (Vector<String>) message.getData()){
			str += s + "<br>";
		}
		
		str += "</body></html>";
		
		JOptionPane.showMessageDialog(null,
				str,
			    "Profile",
			    JOptionPane.INFORMATION_MESSAGE);
	}

}
