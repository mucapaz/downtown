package br.cin.ufpe.Downtown.application.threads;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.SocketWrapper;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class Sender extends BasicThread{
	
	private Vector<Message> buffer;
	
	private SocketWrapper socketWrapper;

	
	private Exceptionable exceptionable;
	
	
	public Sender(Vector<Message> buffer,
			SocketWrapper socketWrapper, Exceptionable exceptionable) {
		this.buffer = buffer;
		this.socketWrapper = socketWrapper;
		this.exceptionable = exceptionable;
	}
	
	
	public void run(){
		
		synchronized (this) {

			
			Message message;
			try {	
				
				while(this.isRunning()) {
					
					if(buffer.size() > 0) {
						message = buffer.remove(0);
						if(this.socketWrapper == null) continue;
						this.socketWrapper.sendMessage(message);
						Logger.info("Sending message > ID: " + message.getId() + " Type: " + message.getType() + " | Data: " + message.getData(), this);
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
				
				
				if(exceptionable != null ) {
					Logger.info("Sender > exceptionable !=null > handling exception", this);
					exceptionable.handleExceptionUser(null);
				}
				
				
				//super.setRunning(false);
				Logger.log("Catched a exception and stoped", this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				if(exceptionable != null ) {
					Logger.info("Sender > exceptionable !=null > handling exception", this);
					exceptionable.handleExceptionUser(null);
				}
			}		
			
		}
		

	}


	public Vector<Message> getBuffer() {
		return buffer;
	}


	public void setBuffer(Vector<Message> buffer) {
		this.buffer = buffer;
	}


	public SocketWrapper getSocketWrapper() {
		return socketWrapper;
	}


	public void setSocketWrapper(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
	}


	public Exceptionable getExceptionable() {
		return exceptionable;
	}


	public void setExceptionable(Exceptionable exceptionable) {
		this.exceptionable = exceptionable;
	}
	
	

}
