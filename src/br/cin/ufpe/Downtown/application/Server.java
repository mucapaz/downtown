package br.cin.ufpe.Downtown.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.Vector;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.UDPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.UDPStarter;
import br.cin.ufpe.Downtown.application.threads.Receiver;
import br.cin.ufpe.Downtown.application.threads.Sender;
import br.cin.ufpe.Downtown.application.threads.ServerMessageHandler;
import br.cin.ufpe.Downtown.application.threads.UserListSender;
import br.cin.ufpe.Downtown.application.utils.Logger;


public class Server implements Exceptionable{
	
	private ServerSocket welcomeSocket;
	
	private int sendUserListTimeout; // in ms
	
	private Vector<ClientServerConnection> clients;

	private UserListSender userListSenderTask;
	

	
	public Server(int port, int timeout) throws IOException{
		
		Logger.info("Initializing TCP server on port " + port, this);
		
		this.sendUserListTimeout = timeout;
		
		this.welcomeSocket = new ServerSocket(port);
		
		this.clients = new Vector<ClientServerConnection>();
		
		ServerMessageHandler messageHandler = new ServerMessageHandler(this);
		new Thread(messageHandler).start();
		
		userListSenderTask = new UserListSender(this.clients);
		
		Timer userListSenderTimer = new Timer();
		userListSenderTimer.schedule(userListSenderTask, 0, this.sendUserListTimeout);

		UDPSocketWrapper udpServer = null;
		
	//	UDPSocketWrapper udpServerAccept = null;
		
	//	int minhaMira = 43000;
	//	int mySender = 44000; 
	//	int myReceiverSocketNumber = 30000;
		int p2pPortUser = 32000;
		UDPStarter serverSocket = null;
		if(!Configuration.isTCP()) {
			//udpServerAccept = new UDPSocketWrapper("localhost", 0, mySender, myReceiverSocketNumber, 0);
			serverSocket = new UDPStarter("SERVER");
		}
		
	
		
		while(true){
			

			if(Configuration.isTCP()){
				
				Socket socket = this.welcomeSocket.accept();
				
				Logger.log("New client connected - Number of connected clients (" + (this.clients.size() + 1) + ")", this);
				
				SocketWrapper wrapper = null;
				
				wrapper = new TCPSocketWrapper(socket);
				
				ClientServerConnection connection = new ClientServerConnection(wrapper, ++port, this);
				
				clients.add(connection);
			}
			else {
				
				try {
					
					while(true) {
						
					
					System.out.println("Esperando conexao udp");
					UDPSocketWrapper udpConnection = serverSocket.accept(++p2pPortUser);
					System.out.println("Conexao aceita");
					
					ClientServerConnection connection = new ClientServerConnection(udpConnection, p2pPortUser, this);
					
					
					System.out.println("Connection criada");
					
					this.clients.add(connection);
					
					
					//udpServer = new UDPSocketWrapper("localhost", 53040, 45455, 30000, 0);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			
		}
		
	}
	


	public Server(int port) throws IOException{
		
		new Server(port, 5000);
		
	}
	
	
	public ServerSocket getWelcomeSocket() {
		return welcomeSocket;
	}


	public void setWelcomeSocket(ServerSocket welcomeSocket) {
		this.welcomeSocket = welcomeSocket;
	}


	public int getSendUserListTimeout() {
		return sendUserListTimeout;
	}


	public void setSendUserListTimeout(int sendUserListTimeout) {
		this.sendUserListTimeout = sendUserListTimeout;
	}


	public Vector<ClientServerConnection> getClients() {
		return clients;
	}


	public void setClients(Vector<ClientServerConnection> clients) {
		this.clients = clients;
	}
	
	/**Se o cara saiu da lista, ent�o tira ele da lista de usu�rios online e reenvia a listas
	 * @throws IOException */
	public void removeUser(User user) throws IOException{ 
		
		synchronized (this) {
			Logger.info("TCPServer > removerUser > User:" + user, this);
			ClientServerConnection client;
			for(int i=0; i < this.clients.size(); i++){
				client = this.clients.get(i);
				if(client.getUser()!=null && client.getUser() == user) {
					Logger.info("TCPServer > removerUser > User encontrado para remover:" + user, this);
					this.clients.remove(i);
					client.close();
					break;
				}
				
			}			
			this.userListSenderTask.sendUserList();
			
			
		}
		

	}


	@Override
	public void handleExceptionUser(User user) {
		Logger.info("TCPServer > handling exceptionable > User: " + user, this);
		try {
			this.removeUser(user);
			Logger.log("Usuario : "  + user + " tratado", this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			
			
			//e.printStackTrace();
		}
		
	}


}
