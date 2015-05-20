package br.cin.ufpe.Downtown.application.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;







import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.Configuration;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class ClientAccepterTCP extends BasicThread {

	private ServerSocket welcomeSocket;
	private Vector<Receiver> anonymousReceivers;
	private Chat chat;

	public ClientAccepterTCP(ServerSocket welcomeSocket, Chat chat) {
		super();
		this.welcomeSocket = welcomeSocket;
		this.chat = chat;
	}
	
	public ClientAccepterTCP(Chat chat) {
		super();
		this.welcomeSocket = null;
		this.chat = chat;
	}

	@Override
	public void run() {
		TCPSocketWrapper tcpSocketWrapper;
		Receiver receiver;
		Socket socket;
		
		synchronized(this){
			while(super.isRunning()){
				
				
				try {
					
					socket = welcomeSocket.accept();	
					
					Logger.info("New socket Accepted ["+socket+"]", this);
					
					tcpSocketWrapper = new TCPSocketWrapper(socket);
					
					receiver = new Receiver(new Vector<Message>(), tcpSocketWrapper, this.chat);
					
					new Thread(receiver).start();
					
					this.chat.getReceivers().add(receiver);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}
	}
}
