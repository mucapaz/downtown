package br.cin.ufpe.Downtown.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.P2PConnection;
import br.cin.ufpe.Downtown.application.entities.FileWrapper;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JPopupMenu;

import java.awt.Component;

public class ConversationPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private P2PConnection connection;
	private final JPanel  panel_2;
	private final JScrollPane scrollPane;
	private Chat chat;
	
public P2PConnection getConnection() {
		return connection;
	}


	public void setConnection(P2PConnection connection) {
		this.connection = connection;
	}

	public ConversationPanel(final P2PConnection connection, Chat chat) {
		
		this.chat = chat;
		this.connection = connection;
		setBorder(null);
		
		setPreferredSize(new Dimension(740,530));
		setMinimumSize(new Dimension(740,530));
		setMaximumSize(new Dimension(740,530));
		setSize(new Dimension(740,530));
		setBounds(0, 0, 740, 530);
		setLayout(null);
		setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		setBackground(new Color(245,245,243));
		
		final JTextPane txtpnHitEnterTo = new PlaceholderTextArea("Hit enter to send");
		
		txtpnHitEnterTo.setFont(new Font("Arial", Font.PLAIN, 13));
		//txtpnHitEnterTo.setText("Hit enter to send");
		txtpnHitEnterTo.setBounds(21, 421, 694, 78);
		txtpnHitEnterTo.setBorder(BorderFactory.createLineBorder(new Color(175,175,175)));
		add(txtpnHitEnterTo);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(0, 0, 741, 72);
		add(panel);
		panel.setBackground(new Color(225,225,225));
		panel.setLayout(null);
		
		JLabel lblTalkingToDuhan = new JLabel("<html><b>Talking to </b>" + connection.getUser().getName() + "</html>");
		lblTalkingToDuhan.setBounds(27, 30, 300, 14);
		panel.add(lblTalkingToDuhan);
		
		JButton profileButton = new JButton(new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/profile_bt.jpg")));
		profileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				connection.getToClientMessages().add(new Message("REQUEST_PROFILE", null));
				
			}
		});
		
		profileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		profileButton.setBorder(BorderFactory.createEmptyBorder());
		profileButton.setContentAreaFilled(false);
		profileButton.setBounds(596, 18, 124, 35);
		panel.add(profileButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(21, 100, 694, 273);
		add(panel_1);
		panel_1.setLayout(null);
		
		panel_1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 694, 273);
		panel_1.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		panel_2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		final JPanel panel_3 = new JPanel();
		panel_3.setBounds(21, 384, 694, 37);
		panel_3.setBackground(new Color(47,47,47));
		add(panel_3);
		panel_3.setLayout(null);
		
		final JFileChooser fileDialog = new JFileChooser();
		
		final ImageIcon fileIcon = new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/file_bt_hover.jpg"));
		final ImageIcon hoverFileIcon = new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/file_bt.jpg"));
		
		final JButton fileButton = new JButton(fileIcon);
		fileButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
                fileButton.setIcon(hoverFileIcon);
            }
            public void mouseExited(MouseEvent evt)
            {
               fileButton.setIcon(fileIcon);
            }
            public void mousePressed(MouseEvent evt)
            {
            	fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int openChoice = fileDialog.showOpenDialog(null);
            	if (openChoice == JFileChooser.APPROVE_OPTION) {
            			String path = fileDialog.getSelectedFile().getAbsolutePath();

            			FileWrapper fileWrapper;
						try {
							fileWrapper = FileWrapper.fromFile(path.substring(0,path.length() - fileDialog.getSelectedFile().getName().length()), fileDialog.getSelectedFile().getName());
							Message msg = new Message("FILE", fileWrapper);
							connection.getToClientMessages().add(msg);
							addFile(msg, true);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null,
								    "I'm sorry, we couldn't send the file :(",
								    "Error",
								    JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
    	 				
    	 				
            			
            	}
            	
            }
            public void mouseReleased(MouseEvent evt)
            {
               
            }
        });
		
		fileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		fileButton.setBorder(BorderFactory.createEmptyBorder());
		fileButton.setContentAreaFilled(false);
		fileButton.setBounds(0, 0, 100, 37);
		panel_3.add(fileButton);
		
		
		final JPopupMenu popup = new JPopupMenu();
	    ActionListener menuListener = new ActionListener() {
	      public void actionPerformed(ActionEvent event) {
	        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Popup menu item ["
	            + event.getActionCommand() + "] was pressed.");
	        
	        String em = event.getActionCommand();
	        int i = 1;
	        if(em.equalsIgnoreCase("Happy")){
	        	i = 1;
	        }else if(em.equalsIgnoreCase("Cool")){
	        	i = 2;
	        }else if(em.equalsIgnoreCase("Sad")){
	        	i = 3;
	        }else if(em.equalsIgnoreCase("Doubt")){
	        	i = 4;
	        }else if(em.equalsIgnoreCase("Surprised")){
	        	i = 5;
	        }else if(em.equalsIgnoreCase("Perrusi")){
	        	i = 6;
	        }
	        
	        Message msg = new Message("EMOTICON", i);
			connection.getToClientMessages().add(msg);
			addEmoticon(msg, true);
	        
	      }
	    };
	    JMenuItem item;
	    
	    popup.add(item = new JMenuItem("Happy", new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/1.png"))));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
	    popup.add(item = new JMenuItem("Cool", new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/2.png"))));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
	    popup.add(item = new JMenuItem("Sad", new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/3.png"))));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
	    popup.add(item = new JMenuItem("Doubt", new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/4.png"))));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
	    popup.add(item = new JMenuItem("Surprised", new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/5.png"))));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	        
	    popup.add(item = new JMenuItem("Perrusi", new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emo/6.png"))));
	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
	    item.addActionListener(menuListener);
	    
		final ImageIcon emoIcon = new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emoticon_bt.jpg"));
		final ImageIcon hoverEmoIcon = new ImageIcon(Main.class.getResource("/br/cin/ufpe/Downtown/gui/assets/emoticon_bt_hover.jpg"));
		
		final JButton emoButton = new JButton(emoIcon);
		emoButton.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent evt)
            {
            	emoButton.setIcon(hoverEmoIcon);
            }
            public void mouseExited(MouseEvent evt)
            {
            	emoButton.setIcon(emoIcon);
            }
            public void mousePressed(MouseEvent evt)
            {
            	popup.show(emoButton, evt.getX(), evt.getY());
            	
            }
            public void mouseReleased(MouseEvent evt)
            {
               
            }
        });
		
		emoButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		emoButton.setBorder(BorderFactory.createEmptyBorder());
		emoButton.setContentAreaFilled(false);
		emoButton.setBounds(100, 0, 138, 37);
		panel_3.add(emoButton);
		
		
		txtpnHitEnterTo.addKeyListener(new KeyListener(){
		    @Override
		    public void keyPressed(KeyEvent e){
		        if(e.getKeyCode() == KeyEvent.VK_ENTER){
		        	e.consume();
		        	Message msg = new Message("TEXT", txtpnHitEnterTo.getText());
		        	connection.getToClientMessages().add(msg);
		        	addMessage(msg, true);
		        	txtpnHitEnterTo.setText("");
		        }
		    }

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	public void addMessage(Message message, boolean myself){
		String name;
		if(myself){
			name = this.chat.getUser().getName();
		}else{
			name = this.connection.getUser().getName();
		}
		MessagePanel msgPanel = new MessagePanel(name,(String)message.getData(), myself);
		panel_2.add(msgPanel);
		if(this.isVisible()){
			panel_2.revalidate();
	    	scrollPane.validate();
		}
    	JScrollBar vertical = scrollPane.getVerticalScrollBar();
    	vertical.setValue( vertical.getMaximum() + 60 );
	}
	
	public void addMessage(Message message){
		addMessage(message, false);
	}


	public void addFile(Message message, boolean myself){
		String name;
		if(myself){
			name = this.chat.getUser().getName();
		}else{
			name = this.connection.getUser().getName();
		}
		FilePanel msgPanel = new FilePanel(name,(FileWrapper)message.getData(), myself);
		panel_2.add(msgPanel);
		if(this.isVisible()){
			panel_2.revalidate();
	    	scrollPane.validate();
		}
    	JScrollBar vertical = scrollPane.getVerticalScrollBar();
    	vertical.setValue( vertical.getMaximum() + 60 );
	}
	
	public void addFile(Message message) {
		
		addFile(message, false);
		
	}
	
	
	public void addEmoticon(Message message, boolean myself){
		String name;
		if(myself){
			name = this.chat.getUser().getName();
		}else{
			name = this.connection.getUser().getName();
		}
		EmoticonPanel msgPanel = new EmoticonPanel(name,(int)message.getData(), myself);
		panel_2.add(msgPanel);
		if(this.isVisible()){
			panel_2.revalidate();
	    	scrollPane.validate();
		}
    	JScrollBar vertical = scrollPane.getVerticalScrollBar();
    	vertical.setValue( vertical.getMaximum() + 60 );
	}
	
	public void addEmoticon(Message message) {
		
		addEmoticon(message, false);
		
	}
	
	
}
