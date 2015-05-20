package br.cin.ufpe.Downtown.application;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.threads.Receiver;
import br.cin.ufpe.Downtown.application.threads.Sender;

public class Connection {

	private Vector<Message> toClientMessages;
	
	private Vector<Message> fromClientMessages;
	
	private SocketWrapper socketWrapper;
	
	private Receiver fromClientReceiver;
	
	private Sender toClientSender;
	
	private User user;

	private boolean authenticated;
	
	public Connection() {
		
		this.toClientMessages = new Vector<Message>();
		
		this.fromClientMessages = new Vector<Message>();

		
		
		this.authenticated = false;
	}
	
	
	public Connection(SocketWrapper socketWrapper) throws IOException{
		
		this.socketWrapper = socketWrapper;
		
		this.toClientMessages = new Vector<Message>();
		
		this.fromClientMessages = new Vector<Message>();
		

		
	}

	
	public void initIO(Exceptionable exceptionable){
		this.fromClientReceiver = new Receiver(this.fromClientMessages, this.socketWrapper, exceptionable);
		new Thread(this.fromClientReceiver).start();

		this.toClientSender = new Sender(this.toClientMessages, this.socketWrapper, exceptionable);
		new Thread(this.toClientSender).start();		
	}
	
	
	public Connection(SocketWrapper socketWrapper, User user) throws IOException{
		this.socketWrapper = socketWrapper;
		
		this.toClientMessages = new Vector<Message>();
		
		this.fromClientMessages = new Vector<Message>();
		
		this.user = user; 
		this.authenticated = false;
	}
	



	public User getUser() {
		return user;
		
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Vector<Message> getToClientMessages() {
		return this.toClientMessages;
	}

	public void setToClientMessages(Vector<Message> toClientMessages) {
		this.toClientMessages = toClientMessages;
	}


	public Vector<Message> getFromClientMessages() {
		return this.fromClientMessages;
	}


	public void setFromClientMessages(Vector<Message> fromClientMessages) {
		this.fromClientMessages = fromClientMessages;
	}


	public SocketWrapper getSocketWrapper() {
		return socketWrapper;
	}


	public void setSocketWrapper(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
	}


	public Receiver getFromClientReceiver() {
		return fromClientReceiver;
	}


	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public void setFromClientReceiver(Receiver fromClientReceiver) {
		this.fromClientReceiver = fromClientReceiver;
	}


	public Sender getToClientSender() {
		return toClientSender;
	}


	public void setToClientSender(Sender toClientSender) {
		this.toClientSender = toClientSender;
	}
	
	
	
}
