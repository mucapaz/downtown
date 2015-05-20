package br.cin.ufpe.Downtown.application.threads;

import java.io.IOException;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;

import br.cin.ufpe.Downtown.application.ClientServerConnection;
import br.cin.ufpe.Downtown.application.Server;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class ServerMessageHandler extends BasicThread{

	private Server server;

	public ServerMessageHandler(Server server){
		this.server = server;

	}

	public void run(){
		
		synchronized (this) {
			Vector<ClientServerConnection> clientList = this.server.getClients();
			Vector<Message> buffer;
			
			String type;
			User user;
			boolean remove;
			Message message;
			ClientServerConnection client;
			
			
			int apagar =0 ;
			
			try {
				
				while(true){	
			
					
					synchronized (clientList) {
						
						
						if ( clientList.isEmpty() ) {
							
							
							if(  (apagar) == 0 )
								//Logger.info("Client list is empty", this);
							
							apagar ++;
							apagar %=5000;
							
							
							
							continue;
						}
						else {
							remove = false;
							for(int i=0; i< clientList.size(); i++){	
									client = clientList.get(i);
									buffer = client.getFromClientMessages();	
									int messageAtThisTime = buffer.size();
									for(int j =0; j < messageAtThisTime; j++){
										if(j >= buffer.size()) continue;
										message = buffer.get(j);
										type = message.getType();			
										if(type.equals("USER_INFO")){	
											Logger.log("Retrieving user information", this);
											user = (User) message.getData();			
											client.setUser(user);
											Logger.log("User with name " + user.getName() + " connected", this);
										}else if(type.equals("CLOSE_CONNECTION")){	
											Logger.log("CLOSE_CONNCETION Message received", this);
											clientList.remove(client);
											client.close();
										}
										buffer.remove(j);
									}
							}	
						}
					}

					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

		}
		
					
	}

}
