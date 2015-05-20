package br.cin.ufpe.Downtown.application.socket.udpSocketWrapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;

import br.cin.ufpe.Downtown.application.entities.Message;


public class SenderUDP {


	public static final int MSS = 200;

	// Probability of loss
	public static final double PROBABILITY = 0.1;

	public static final int WINDOW_SIZE = 2;

	public static final int TIMER = 30;

	public String receiverIP;
	public int receiverSocketNumber;
	public int mySocketNumber;
	DatagramSocket toReceiver;
	public long timeLimit;
	

	public SenderUDP(String receiverIP, int receiverSocketNumber,int mySocketNumber, int timeLimit) throws SocketException{
		this.receiverIP= receiverIP;
		this.receiverSocketNumber = receiverSocketNumber;
		this.mySocketNumber = mySocketNumber;
		System.out.println("@ Chegou 2");
		toReceiver = new DatagramSocket(mySocketNumber);
		this.timeLimit = timeLimit*1000;
	}
	public DatagramSocket getToReceiver(){
		return toReceiver;
	}
	
	public synchronized void sendUDP(Message msg) throws Exception{
		
		long lastAckTime = System.currentTimeMillis();
		
		
		
		System.out.println("enviado msg");
		int lastSent = 0;
		int waitingForAck = 0;
		byte[] fileBytes = SerializerUDP.toBytes(msg);

		System.out.println("Data size: " + fileBytes.length + " bytes");

		// Last packet sequence number
		int last_seq = (int) Math.ceil( (double) fileBytes.length / MSS);

		System.out.println("Number of packets to send: " + last_seq);


		System.out.println("chegou 12");
		
		System.out.println("mySockletNumber: " + mySocketNumber);
		
		System.out.println("chegou 13");
		
		
		//	System.out.println(toReceiver.getLocalSocketAddress());
		InetAddress receiverAddress = InetAddress.getByName(receiverIP);
		//	System.out.println(receiverAddress + " receiver adress");

		ArrayList<RDTPacketUDP> sent = new ArrayList<RDTPacketUDP>();
			
		while(true){
			


			while(lastSent - waitingForAck < WINDOW_SIZE && lastSent < last_seq){


				byte[] filePacketBytes = new byte[MSS];


				filePacketBytes = Arrays.copyOfRange(fileBytes, lastSent*MSS, lastSent*MSS + MSS);


				RDTPacketUDP rdtPacketObject = new RDTPacketUDP(lastSent, filePacketBytes, (lastSent == last_seq-1) ? true : false);
				byte[] sendData = SerializerUDP.toBytes(rdtPacketObject);
				System.out.println("chegou 14");	
				DatagramPacket packet = new DatagramPacket(sendData, sendData.length, receiverAddress, receiverSocketNumber);
				
			
				System.out.println("IP = " + receiverAddress + " socket MIRADO = " + receiverSocketNumber);
				
				System.out.println("chegou 15");

				
				sent.add(rdtPacketObject);

				if(Math.random()> PROBABILITY  )  {
					toReceiver.send(packet);

									
			   }

				lastSent++;	
				System.out.println("chegou 16");

			} // End of sending while

			byte[] ackBytes = new byte[100];

			DatagramPacket ack = new DatagramPacket(ackBytes, ackBytes.length);

			try{
					toReceiver.setSoTimeout(TIMER);
					toReceiver.receive(ack);
					lastAckTime = System.currentTimeMillis();
					//System.out.println("chegou 16 - ack recebido");

					Object obj = SerializerUDP.toObject(ack.getData());
					

					RDTAckUDP ackObject = (RDTAckUDP) obj;
					//	System.out.println("Received ACK for " + ackObject.getPacket());

					if(ackObject.getPacket() == last_seq){
						break;
					}

					waitingForAck = Math.max(waitingForAck, ackObject.getPacket());
				
			}catch(SocketTimeoutException e){
				
				
				
				if( System.currentTimeMillis() - lastAckTime > timeLimit){
					 throw new TimeLimitException("Tempo de envio excedeu o limite esperado.");
				}				
				
				
				System.out.println("NÃO CHEGOU NADA E O TEMPORIZADOR ESTOUROU.      Ultimo pacote que recebeu ACK = " +  waitingForAck);
				
				
				for(int i = waitingForAck; i < lastSent; i++){

					byte[] sendData = SerializerUDP.toBytes(sent.get(i));
					DatagramPacket packet = new DatagramPacket(sendData, sendData.length, receiverAddress, receiverSocketNumber);

					if(Math.random()>PROBABILITY)	toReceiver.send(packet);	


					System.out.println("i = " + i );
					//System.out.println("REsending packet with sequence number " + sent.get(i).getSeq() +  " and size " + sendData.length + " bytes");
				}
			}


		}

		System.out.println("Finished transmission");
		
	}
	

}
