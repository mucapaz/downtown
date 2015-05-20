package br.cin.ufpe.Downtown.application.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.ReceiverUDP;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.SenderUDP;
import br.cin.ufpe.Downtown.application.threads.Sender;

public class UDPSocketWrapper implements SocketWrapper{
/*Samuel vai fazer*/

	
	ReceiverUDP receiver;
	SenderUDP sender;
	
	String receiverIP;
	int receiverSocketNumber;
	int mySenderSocketNumber;
	int myReceiverSocketNumber;
	int myAckReceiverSocketNumber;
	
	
	/**
	 * 
	 * <p> receiveSocketNumber = destino
	 * <p> mySenderSocketNumber = número da porta do meu sender 
	 * <p> myReceiverSocketNumber = meu número de porta de entrada ( me enviam nesta porta ) 
	 * <p> myAckReceiver  = inútil 
	 * */
	public UDPSocketWrapper(String receiverIP, int receiverSocketNumber, int mySenderSocketNumber, int myReceiverSocketNumber,
			int myAckReceiverSocketNumber) throws SocketException{
		
			
			this.receiverIP = receiverIP;
			this.receiverSocketNumber = receiverSocketNumber;
			this.mySenderSocketNumber = mySenderSocketNumber;
			this.myReceiverSocketNumber= myReceiverSocketNumber;
			this.myAckReceiverSocketNumber = myAckReceiverSocketNumber;
			sender = new SenderUDP(receiverIP,receiverSocketNumber,mySenderSocketNumber, 15); // ultimo param tem o timeout 
			receiver = new ReceiverUDP(myReceiverSocketNumber);
			
	}
	
	public void sendMessage(Message message) throws Exception {		
			sender.sendUDP(message);

	}	
	
	@Override		
	public Message receiveMessage() throws Exception {
		return receiver.receive();
	}


	@Override
	public synchronized void close() throws IOException {
		this.receiver.getFromSender().close();
		this.sender.getToReceiver().close();
		
		this.sender = null;
		this.receiver = null;
		
	}

	@Override
	public synchronized String getIP() throws UnknownHostException {
		// TODO Auto-generated method stub
		String ip = InetAddress.getLocalHost().toString();
		ip = ip.substring(ip.indexOf('/') + 1);
		//return ip;
		return receiverIP;
	}

	@Override
	public boolean isSocketed() {
		// TODO Auto-generated method stub
		return false;
	}


}
