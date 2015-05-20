package br.cin.ufpe.Downtown.application.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.SerializerUDP;

public class testeSender {
	public static void main(String[] args) throws Exception{
		
		String receiverIP = "localhost";
		int receiverSocketNumber = 9000;
		int mySocketNumber = 10000;
		
		SocketWrapper sw = new UDPSocketWrapper(receiverIP, receiverSocketNumber, mySocketNumber,mySocketNumber,0);
		
		String objeto = "TESTE 123456789";	
		
		Message msg = new Message("tipo",objeto);
		System.out.println((String)msg.getData());
		
		sw.sendMessage(msg);
		
	}
	

}
