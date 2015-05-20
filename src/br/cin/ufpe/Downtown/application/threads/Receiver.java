package br.cin.ufpe.Downtown.application.threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.utils.Logger;


/**
 * Thread que recebe mensagens e coloca em um buffer de recepção no lado do cliente
 * 
 * */
public class Receiver extends BasicThread {
	
	private Exceptionable execeptionable;
	
	public SocketWrapper getSocketWrapper() {
		return socketWrapper;
	}


	public void setSocketWrapper(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
	}


	private Vector<Message> buffer;
	
	public Vector<Message> getBuffer() {
		return buffer;
	}


	public void setBuffer(Vector<Message> buffer) {
		this.buffer = buffer;
	}


	private SocketWrapper socketWrapper;
	

	public Receiver(Vector<Message> buffer,
			SocketWrapper socketWrapper, Exceptionable execeptionable) {
		this.buffer = buffer;
		this.execeptionable = execeptionable;
		this.socketWrapper = socketWrapper;
	}


	@Override
	public void run() {
		synchronized(this){
	 
			Message message;
			try {		
				while(this.isRunning()) {
						
						Object obj = this.socketWrapper.receiveMessage();

						message = (Message) obj;
						
						synchronized (message) {
							Logger.info(" ID: " + message.getId() + " Type: " + message.getType() + " | Data: " + message.getData(), this);
						}
						
						this.buffer.add(message);
						
						
						if(message.getType().equals("REQUEST_CONNECTION")) {
							User u = (User) message.getData();
							Logger.info("Message received with data: " + u.getName(), this); 
							
						}
						
						
				}
	
			} catch (Exception e) {
				//this.setRunning(false);
				Logger.info("Receiver > handlind exception", this);
				this.execeptionable.handleExceptionUser(null);
				
				//e.printStackTrace();
			}	
		}
			
	}


	public Exceptionable getExeceptionable() {
		return execeptionable;
	}


	public void setExeceptionable(Exceptionable execeptionable) {
		this.execeptionable = execeptionable;
	}
	

	

}
