package br.cin.ufpe.Downtown.application.socket;

import java.io.IOException;
import java.net.UnknownHostException;

import br.cin.ufpe.Downtown.application.Configuration;
import br.cin.ufpe.Downtown.application.entities.Message;

public interface SocketWrapper {

	
	public void sendMessage(Message message) throws Exception;
	
	public Message receiveMessage() throws ClassNotFoundException, IOException, Exception;
	
	public void close() throws IOException;
	
	public String getIP() throws UnknownHostException;
	

	public boolean isSocketed();
	
}
