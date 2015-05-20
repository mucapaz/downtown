package br.cin.ufpe.Downtown.application;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.threads.P2PMessageHandler;
import br.cin.ufpe.Downtown.application.threads.Receiver;
import br.cin.ufpe.Downtown.application.threads.Sender;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class P2PConnection extends Connection implements Exceptionable{
		
	private int port;

	private Chat chat;

	private P2PMessageHandler handler;
	
	private boolean authorized;
	
	/*setar depois o user e socketrapper*/
	public P2PConnection(User user, int port, Chat chat){
		super();
		super.setToClientMessages(new Vector<Message>());
		super.setUser(user);
		this.port = port;
		this.chat = chat;
		this.authorized = false;
	}

	

	public void connect(User chatUser){

		Logger.log("Sending request connection message | Buffer: " + this.getToClientMessages() + " and " + this.getFromClientMessages() , this);
		
		super.getUser().setPort(this.port);
		
		this.getToClientMessages().add(new Message("REQUEST_CONNECTION", chatUser   ));
		
		Logger.log("P2PConnection > Connecting to: " + super.getUser().getName(), this);
		
		
		P2PMessageHandler p2pMessageHandler = new P2PMessageHandler(this, this.getFromClientMessages(), this.chat);
		new Thread(p2pMessageHandler).start();

		
		this.initReceiver();
		this.initSender();

		
		Logger.log("P2PConnection > connect() > User: " + this.getUser().getName() + " auth=true" , this);		
		super.setAuthenticated(true);
		
	}
	
	
	
	
		
	public void initReceiver(){

		super.setFromClientReceiver(new Receiver(super.getFromClientMessages(), super.getSocketWrapper(), this));
		
		
		new Thread(super.getFromClientReceiver()).start();
		
		
	}
	

	public void initSender(){
		
		

		
		super.setToClientSender(new Sender(super.getToClientMessages(), super.getSocketWrapper(), this));
		
		
		new Thread(super.getToClientSender()).start();				
	}
	

	@Override
	public String toString() {
		return "P2PConnection [getUser()=" + getUser()
				+ ", getToClientMessages()=" + ( getToClientMessages().size() )
				+ ", getFromClientMessages()=" + getFromClientMessages()
				+ ", getSocketWrapper()=" + getSocketWrapper()
				+ ", getFromClientReceiver()=" + getFromClientReceiver()
				+ ", isAuthenticated()=" + isAuthenticated()
				+ ", getToClientSender()=" + getToClientSender()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}



	@Override
	public void handleExceptionUser(User user) {
		
		Logger.info("P2PConnection > handling exception", this);
		
		this.chat.handleExceptionUser(super.getUser());
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.port == ((P2PConnection)obj).getPort();
	}
	
	
	public void stopIO() throws IOException{
		
		if(super.isAuthenticated()) {
			super.getFromClientReceiver().stop();
			
			super.getToClientSender().stop();
			
			if(this.getSocketWrapper() != null) this.getSocketWrapper().close();			
		}
		

		
	}



	public boolean isAuthorized() {
		return authorized;
	}



	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	
	
}  
