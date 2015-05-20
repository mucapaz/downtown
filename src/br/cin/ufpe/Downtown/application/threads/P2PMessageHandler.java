package br.cin.ufpe.Downtown.application.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.ClientServerConnection;
import br.cin.ufpe.Downtown.application.Configuration;
import br.cin.ufpe.Downtown.application.P2PConnection;
import br.cin.ufpe.Downtown.application.Server;

import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class P2PMessageHandler extends BasicThread{


	private Vector<Message> buffer;
	
	private P2PConnection connection;
	
	private Chat chat;

	public P2PMessageHandler(P2PConnection connection, Vector<Message> buffer, Chat chat){
		Logger.log("New P2PConnection created", this);
		System.out.println("New P2PMessage Handler created");
		this.buffer = buffer;
		this.chat = chat;
		this.connection = connection;
	}

	public void run(){
		
		boolean print = true;

		synchronized (this) {

			Message message, auxMessage;
			String type;
			
			//try {
			while(this.isRunning()){

				for(int i=0; buffer != null && i < buffer.size(); i++){
					message = buffer.remove(i);
					if(message.getType().equals("TEXT")){
						this.chat.updateGUIMessage(this.connection, message);
					}else if(message.getType().equals("FILE")){
						this.chat.updateGUIFile(this.connection, message);
					}else if(message.getType().equals("EMOTICON")){
						this.chat.updateGUIEmoticon(this.connection, message);
					}else if(message.getType().equals("ACCEPT_CONNECTION")){
						this.connection.setAuthorized(true);
						this.chat.updateGUIAccepted(this.connection, message);
					}else if(message.getType().equals("AUTHORIZE_CONNECTION")){
						this.chat.updateGUIPanel(this.connection);
					}else if(message.getType().equals("REQUEST_PROFILE")){
						Vector<P2PConnection> cons = chat.getP2pConnections();
						Vector<String> msg = new Vector<String>();
						for(P2PConnection c: cons){
							if(c.isAuthorized()) msg.add(c.getUser().getName());
						}
						this.connection.getToClientMessages().add(new Message("PROVIDE_PROFILE", msg));
					}else if(message.getType().equals("PROVIDE_PROFILE")){
						this.chat.updateGUIProfile(connection, message);
					}
				}
			}
		} 		
	}


}
