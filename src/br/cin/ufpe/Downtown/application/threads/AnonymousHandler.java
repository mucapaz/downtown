package br.cin.ufpe.Downtown.application.threads;

import java.util.Vector;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.P2PConnection;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class AnonymousHandler extends BasicThread implements Runnable {
	
	
	private Vector<Receiver> receivers;

	private Vector<P2PConnection> p2pConnections;
	
	private Chat chat;
	
	
	public AnonymousHandler(Vector<Receiver> receivers, Vector<P2PConnection> p2pConnections, Chat chat){
		this.receivers = receivers;
		this.p2pConnections = p2pConnections;
		this.chat = chat;
	}
	
	
	@Override
	public void run() {
		
		Receiver receiver; 
		Vector<Message> messagesReceived;
		Message message;
		User user;
		P2PConnection p2pConnection;
		synchronized(this){
			while(super.isRunning()){
				
				for(int i=0; i < this.receivers.size(); i++){
					
					receiver = this.receivers.get(i);
					
					messagesReceived = receiver.getBuffer();
					
					for(int j=0;j<messagesReceived.size(); j++){
						
						message = messagesReceived.remove(j);
						
						if(message.getType().equals("REQUEST_CONNECTION")) {
							Logger.info("Request connection received", this);
							user = (User) message.getData();
							for(int k=0;k<this.p2pConnections.size(); k++){
								p2pConnection = this.p2pConnections.get(k);
								
								Logger.info("Iterating P2PConnection[Auth:" + p2pConnection.isAuthenticated()+  " ]" + " - User:  " + p2pConnection.getUser().getName() + " Comparing with: " +  user.getName(), this);
								
								if(p2pConnection.getUser().getPort() == user.getPort() && !p2pConnection.isAuthenticated() ){
									
									p2pConnection.setSocketWrapper(receiver.getSocketWrapper()); // coloca o socketwrapper no p2pConnection certo
									
									this.receivers.remove(i);

									p2pConnection.initSender();
									
									receiver.setExeceptionable(p2pConnection);
									p2pConnection.setFromClientReceiver(receiver);
									
									this.chat.updateGUIPanel(p2pConnection);
			
									P2PMessageHandler p2pMessageHandler = new P2PMessageHandler(p2pConnection,receiver.getBuffer(), this.chat);
									new Thread(p2pMessageHandler).start();
									
									p2pConnection.setAuthenticated(true);
									Logger.info("New P2P connection authenticated ["+p2pConnection+"]", this);
									break;
								}
							}
							
							
						}
						
					}
					
					
				}
								
				
				
			}
		}

	}
	
	
	
}
