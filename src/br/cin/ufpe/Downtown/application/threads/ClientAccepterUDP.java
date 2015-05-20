package br.cin.ufpe.Downtown.application.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;







import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.Configuration;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.TCPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.UDPSocketWrapper;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.UDPStarter;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class ClientAccepterUDP extends BasicThread {

	private UDPStarter udpAccepter;
	private Vector<Receiver> anonymousReceivers;
	private Chat chat;

	public ClientAccepterUDP( UDPStarter udpAccepter , Chat chat) {
		super();
		this.chat = chat;
		
		//UDPStarter starter = new UDPStarter(minhaMira, mySender, myReceiverSocketNumber)
		this.udpAccepter = udpAccepter;
	}


	@Override
	public void run() {
		UDPSocketWrapper udpSocketWrapper;
		Receiver receiver;

		synchronized(this){
			while(super.isRunning()){
				
				
				try {
					
					
			
					udpSocketWrapper = this.udpAccepter.accept(0);
					
					receiver = new Receiver(new Vector<Message>(), udpSocketWrapper, this.chat);
					
					new Thread(receiver).start();
					
					this.chat.getReceivers().add(receiver);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		}
	}
}