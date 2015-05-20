package br.cin.ufpe.Downtown.application;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.Random;
import java.util.Scanner;

import javax.sql.rowset.spi.SyncResolver;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.entities.UserListEntry;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.UDPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.UDPStarter;
import br.cin.ufpe.Downtown.application.threads.ClientMessageHandler;
import br.cin.ufpe.Downtown.application.threads.Receiver;
import br.cin.ufpe.Downtown.application.threads.Sender;
import br.cin.ufpe.Downtown.application.utils.Logger;
import br.cin.ufpe.Downtown.gui.Main;


public class Chat implements Exceptionable{
	
	private String serverIP;
	
	private int serverPort;
	
	private int port;
	
	private Vector<UserListEntry> onlineUsers;
	
	private User user;
	
	private Vector<Receiver> receivers;
	

	/** Server **/
	private SocketWrapper server;
	
	private Vector<Message> toServerMessages;
	
	private Vector<Message> fromServerMessages;
	
	private Vector<P2PConnection> p2pConnections;
	
	private Main gui;
	
	private int udpSenderPort = 45000; // porta por onde envia um datagrama udp
	private int udpReceiverPort = 20000; // porta por onde um datagrama chega

	public Chat(String serverIP, int serverPort, User user) throws IOException {
		
		this.serverIP = serverIP;
		
		this.serverPort = serverPort;
		
		this.server = null;
		
		this.port = 0;
		
		this.user = user;
		
		this.gui=null;
	
		this.onlineUsers = new  Vector<UserListEntry>();
		
		this.p2pConnections = new Vector<P2PConnection>();
		
		this.toServerMessages = new Vector<Message>();
		
		this.fromServerMessages  = new Vector<Message>();
		
		Logger.log("Trying to connect to server", this);
		
		
		this.receivers = new Vector<Receiver>();
		
		
				
		
		if(Configuration.isTCP()){
			System.out.println("Iniciando socket");
			Socket serverSocket = new Socket(this.serverIP, this.serverPort);
			System.out.println("Iniciando socketWrapper");
			this.server = new TCPSocketWrapper(serverSocket);
			System.out.println("Sockets criados");


		}
		else {
			
			try {
				
				System.out.println("Criando SOCKET UDP");
				this.server = UDPStarter.connect(serverIP, serverPort, this.udpSenderPort, this.udpReceiverPort, this);
				System.out.println("Socket UDP CRIADO");
				this.udpSenderPort++;
				this.udpReceiverPort++;
				
				
				 				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		Logger.info("Connected to server on " + this.serverIP + ":" + this.serverPort, this);
		
		
		System.out.println("chegou 1 construtor chat");
		Receiver fromServerReceiver = new Receiver(this.fromServerMessages, this.server, this);
		new Thread(fromServerReceiver).start();
		System.out.println("chegou 2 construtor chat");
		Sender toServerSender = new Sender(this.toServerMessages, this.server, this);
		new Thread(toServerSender).start();
		System.out.println("chegou 3 construtor chat");
		
		
		System.out.println("chegou 4 construtor chat");
		ClientMessageHandler messageHandler;
		if(Configuration.isTCP())  messageHandler = new ClientMessageHandler(this);
		else messageHandler = new ClientMessageHandler(this, new UDPStarter(13000, 0, this.udpReceiverPort, false));
		System.out.println("chegou 5 construtor chat");
		new Thread(messageHandler).start();
		
		toServerMessages.add( new Message("USER_INFO", this.user) );

		
		System.out.println("chegou 6 construtor chat");
		if(!Configuration.isTCP())while(this.port == 0 ) {}
		System.out.println("Porta settada -- > " + this.port);
	}
	
	public Chat(String serverIP, int serverPort, User user, Main gui) throws IOException {
		this(serverIP, serverPort, user);
		this.gui = gui;
		
	}
	
	public void connectToClient(String ip, int port) throws UnknownHostException, IOException{
		
		Logger.log("Requesting connection to other client on " + ip + ":" + port, this);

		
		SocketWrapper wrapper = null;
				
		if(Configuration.isTCP()){
			
			Socket socket = new Socket(ip, port);

			wrapper = new TCPSocketWrapper(socket);
		}
		else {
			try {
				
				System.out.println("trying to connect: " +  ip + " - port:" + port);
				
				
				Logger.log("Chegou 1", this);
				wrapper = UDPStarter.connect(ip, port, this.udpSenderPort+25, this.udpReceiverPort, this);
				
				Logger.info("Chegou 2", this);
				this.udpSenderPort++;
				this.udpReceiverPort++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		for(int i=0;i < this.p2pConnections.size(); i++) { // bota o socket no P2PConnection do usu�rio que est� sendo conectado
			if(this.p2pConnections.get(i).getPort() == port) {
				Logger.log("FOUND USER ", this);
				this.p2pConnections.get(i).setSocketWrapper(wrapper);
				this.p2pConnections.get(i).connect(this.user);
				break;
			}
		}

	}
	
	public void updateGUIUserList(){
		if(this.gui!=null) {
			this.gui.updateUserList(this.onlineUsers);			
		}
	}
	
	public Vector<Receiver> getReceivers() {
		return receivers;
	}

	public void setReceivers(Vector<Receiver> receivers) {
		this.receivers = receivers;
	}

	public Vector<P2PConnection> getP2pConnections() {
		return p2pConnections;
	}

	public Vector<UserListEntry> getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(Vector<UserListEntry> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public Vector<P2PConnection> getP2PConnections() {
		return p2pConnections;
	}

	public void setP2pConnections(Vector<P2PConnection> p2pConnections) {
		this.p2pConnections = p2pConnections;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SocketWrapper getServer() {
		return server;
	}

	public void setServer(SocketWrapper server) {
		this.server = server;
	}

	public Vector<Message> getToServerMessages() {
		return toServerMessages;
	}

	public void setToServerMessages(Vector<Message> toServerMessages) {
		this.toServerMessages = toServerMessages;
	}

	public Vector<Message> getFromServerMessages() {
		return fromServerMessages;
	}

	public void setFromServerMessages(Vector<Message> fromServerMessages) {
		this.fromServerMessages = fromServerMessages;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}
	
	
	public void openFile(String dir){
		
	}
	
	public P2PConnection findP2PConnection(User user){
		P2PConnection answer = null;
		// acha a p2p connection q deu merda
		for(P2PConnection conn : this.p2pConnections ){
			if(conn.getUser().equals(user)){
				answer = conn;
				break;
			}
		}
		return answer;
	}

	@Override
	public void handleExceptionUser(User user) {
		
		Logger.info("Chat > handling exception [ User:" + user + " ]", this);
		
		P2PConnection p2pConn = null;;
		
		synchronized (this) {
			
				// acha a p2p connection q deu merda
				for(int i=0; i<this.p2pConnections.size(); i++){
					p2pConn = this.p2pConnections.get(i);
					if(user!=null && p2pConn !=null && p2pConn.getUser()!=null && p2pConn.getUser().equals(user)) { // tiro o cara da lista de usu�rios para n ser mais exibido
						
						Logger.info("p2pConnection ["+p2pConn+"] removed from p2pConnection list", this);
						if(p2pConn.getSocketWrapper() != null) {
							try {
								p2pConn.getSocketWrapper().close();
							} catch (IOException e) {
								System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
								e.printStackTrace();
							}
							
							this.p2pConnections.remove(i);
							break;							
						}
						

					}
				} 
				
								
			

			try {
				if(p2pConn != null ) p2pConn.stopIO(); // para todo fluxo de entrada com essa p2pConn
			} catch (IOException e) {
				Logger.info("Chat > IOException no socketWrapper do P2PConnection", this);
			}
			
			// remove o usu�rio da lista de usu�rios online (se ela ainda n foi atualizada ) 
			for(int i=0;i<this.onlineUsers.size(); i++) {
				if(this.onlineUsers.get(i).getUser().equals(p2pConn.getUser())) {
					Logger.info("User  + [" + this.onlineUsers.get(i) + "] removed from online Users list", this);
					this.onlineUsers.remove(i);
				}
			}
			
			this.updateGUIUserList(); // agora da update na gui
			
		}
	}


	public void updateGUIMessage(P2PConnection connection, Message message) {
		
		this.gui.updateGUIMessage(connection, message);
		
	}

	public void updateGUIPanel(P2PConnection p2pConnection) {
		
		this.gui.updateGUIPanel(p2pConnection);
		
	}

	public void updateGUIFile(P2PConnection connection, Message message) {
		this.gui.updateGUIFile(connection, message);
		
	}

	public void updateGUIEmoticon(P2PConnection connection, Message message) {
		this.gui.updateGUIEmoticon(connection, message);
		
	}

	public void updateGUIAccepted(P2PConnection connection, Message message) {
		this.gui.updateGUIAccepted(connection, message);
		
	}

	public void updateGUIProfile(P2PConnection connection, Message message) {
		this.gui.updateGUIProfile(connection, message);
		
	}
	
}
