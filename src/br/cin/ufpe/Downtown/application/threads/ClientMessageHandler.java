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
import br.cin.ufpe.Downtown.application.socket.UDPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.UDPStarter;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class ClientMessageHandler extends BasicThread{


	private Chat chat;
	
	private UDPStarter udpStarter;

	public ClientMessageHandler(Chat chat){
		this.chat = chat;
	}


	public ClientMessageHandler(Chat chat, UDPStarter udpStarter){
		this.chat = chat;
		this.udpStarter = udpStarter;
	}

	
	
	public void run(){

		synchronized (this) {
			Vector<Message> buffer = this.chat.getFromServerMessages();
			String type;
			Vector<UserListEntry> usersOnline;
			Vector<UserListEntry> aux;
			UserListEntry userListEntry;
			User user;
			P2PConnection p2pConnection;
			boolean equal;
			Message message;
			//try {
			while(true){

				for(int i=0; i < buffer.size(); i++){
					message = buffer.remove(i);
					type = message.getType();
					
					if(type.equals("USER_LIST")){
						usersOnline = (Vector<UserListEntry>) message.getData();		
						for(int j=0; j < usersOnline.size(); j++){
							if(usersOnline.get(j).getPort() == this.chat.getPort()) {			 // RETIRA SE FOR IGUAL		
								usersOnline.remove(j);
							}												
						}
				
						aux = this.chat.getOnlineUsers();
						
						
						/*Atualiza a lista de P2P conections*/
						for(int j=0; j<usersOnline.size();j++){
							userListEntry = usersOnline.get(j); // foi samuel que fez
							equal = false;
							for(int k =0; k<aux.size() && !equal; k++) { 
								
								equal = userListEntry.getPort() == aux.get(k).getPort() ;
								
							}
							
							if(!equal) {
								p2pConnection = new P2PConnection(userListEntry.getUser(), userListEntry.getPort(), this.chat);
								Logger.info("New user ["  + userListEntry.getUser().getName() +  "] added to P2PConnections vector " + " - toClient: " + p2pConnection.getToClientMessages() + " - fromClient: " + p2pConnection.getFromClientMessages(), this);
								chat.getP2PConnections().add(p2pConnection);
							}
						}
						
						
						
						this.chat.setOnlineUsers(usersOnline);
						this.chat.updateGUIUserList();
					}else if(type.equals("SET_PORT")){
						this.chat.setPort((Integer) message.getData());
						
						Logger.log("Initializing server on port " + message.getData(), this);
						
						
						Runnable accepter = null;  // accept conections
						
						try {						
							if(Configuration.isTCP()){ 
									ServerSocket serverSocket = new ServerSocket(this.chat.getPort());					
									accepter = new ClientAccepterTCP(serverSocket, this.chat);
							}
							else { // Using UDP
								try {
									this.udpStarter.setMyReceiverSocketNumber(this.chat.getPort());
									this.udpStarter.initSocket();
									accepter = new ClientAccepterUDP(this.udpStarter, this.chat);							
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						new Thread(accepter).start();
						AnonymousHandler anonymusHandler = new AnonymousHandler(this.chat.getReceivers(), this.chat.getP2pConnections(), this.chat);
						new Thread(anonymusHandler).start();
									
						
						
						
						
						
					}


				}


			}
		} /*catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	*/			
	}




	public Chat getChat() {
		return chat;
	}




	public void setChat(Chat chat) {
		this.chat = chat;
	}




	public UDPStarter getUdpStarter() {
		return udpStarter;
	}




	public void setUdpStarter(UDPStarter udpStarter) {
		this.udpStarter = udpStarter;
	}


}
