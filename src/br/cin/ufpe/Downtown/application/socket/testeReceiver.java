package br.cin.ufpe.Downtown.application.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.SerializerUDP;

public class testeReceiver {
	public static void main(String[] args) throws Exception{
		
		
		
		String receiverIP = "localhost";
		System.out.println("IP DO RECEIVER:" +  receiverIP );
		int receiverSocketNumber = 8111;// n importa
		int mySocketNumber = 9000;
		
		
		SocketWrapper sw = new UDPSocketWrapper(receiverIP, receiverSocketNumber, mySocketNumber,mySocketNumber,0);

		Message msg = sw.receiveMessage();

		System.out.println("PEGOU CARAI: " + (String)msg.getData() + "        " +msg.getType());
		

	}


}
