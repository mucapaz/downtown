package br.cin.ufpe.Downtown.application.threads;

import java.net.UnknownHostException;
import java.util.TimerTask;
import java.util.Vector;

import br.cin.ufpe.Downtown.application.ClientServerConnection;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class UserListSender extends TimerTask {

	private Vector<ClientServerConnection> clients;

	public UserListSender(Vector<ClientServerConnection> clients) {
		this.clients = clients;
	}
	
	public void sendUserList(){
		ClientServerConnection client;
		synchronized (this) {
			Vector<UserListEntry> clientList;
			clientList = new Vector<UserListEntry>();

			Logger.log("Preparing user list", this);
			clientList.clear();
			
			
			for(int i =0; i < this.clients.size(); i++){
				client = this.clients.get(i);
				if(client.isAuthenticated()){
					try {
						clientList.add(new UserListEntry(client.getUser(), client.getIP(), client.getPort() ));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}

			for(int i =0; i < this.clients.size(); i++){
				client = this.clients.get(i);
				client.getToClientMessages().add(new Message("USER_LIST", clientList));
			}

		}
	}

	public void run(){
		
		this.sendUserList();
		
	}


}
