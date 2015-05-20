package br.cin.ufpe.Downtown.application;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.Vector;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.threads.Receiver;
import br.cin.ufpe.Downtown.application.threads.Sender;
import br.cin.ufpe.Downtown.application.utils.Logger;


public class ClientServerConnection extends Connection implements Exceptionable{
	

	private boolean authenticated;
	
	private int clientWelcomePort;
	
	private Exceptionable exceptionable;
	
	
	public ClientServerConnection(SocketWrapper socketWrapper, int port, Exceptionable exceptionable) throws IOException{
		
		super(socketWrapper);
		
		super.initIO(this);
		
		this.clientWelcomePort = port;
		
		this.authenticated = false;	
		
		this.getToClientMessages().add(new Message("SET_PORT", port));
		
		this.exceptionable = exceptionable;

	}

	
	
	

	public void close() throws IOException{
		this.getFromClientReceiver().stop();
		this.getToClientSender().stop();
		this.getSocketWrapper().close();
	}
	

	
	

	public void setUser(User user) {
		this.authenticated = true;
		super.setUser(user);
	}


	public int getClientWelcomePort() {
		return clientWelcomePort;
	}


	public void setClientWelcomePort(int clientWelcomePort) {
		this.clientWelcomePort = clientWelcomePort;
	}

	
	public String getIP() throws UnknownHostException {
		return this.getSocketWrapper().getIP();
	}

	public int getPort() {
		return this.clientWelcomePort;
	}
	
	
	public boolean isAuthenticated() {
		return authenticated;
	}


	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}


	/**Compara pela porta do usuário*/
	@Override
	public boolean equals(Object obj) {
		
		return this.clientWelcomePort == ( (ClientServerConnection) obj ).getClientWelcomePort();
	}


	@Override
	public void handleExceptionUser(User user) {
		
		Logger.log("ClientServerConnection > handling exception >  User: " +  super.getUser(), this);
		
		
		this.exceptionable.handleExceptionUser(super.getUser());
	}
	
	

}
