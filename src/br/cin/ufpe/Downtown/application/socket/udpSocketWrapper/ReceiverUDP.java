package br.cin.ufpe.Downtown.application.socket.udpSocketWrapper;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.utils.Logger;


public class ReceiverUDP {

	public static final double PROBABILITY = 0;

	DatagramSocket fromSender;


	public ReceiverUDP(int mySocketNumber) throws SocketException{
		System.out.println("Abrindo receptor com socketNumber = " + mySocketNumber); 
		this.fromSender = new DatagramSocket(mySocketNumber); 
	}

	public DatagramSocket getFromSender(){
		return fromSender;
	}

	public synchronized Message receive() throws Exception{

		byte[] receivedData = new byte[1543];

		int waitingFor = 0;

		byte[] msgByte = null;

		boolean lastOne = false;

		ArrayList<RDTPacketUDP> received = new ArrayList<RDTPacketUDP>();
		Logger.info("receive começou", this);
		while(true){

			//System.out.println(" ------------------------------------------------------------ ");
			//System.out.println("Waiting for packet");

			// Receive packet
			DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);


			/*
			 * Se limite exceder vai lançar SocketTimeoutException 
			 */

			
			fromSender.receive(receivedPacket);



			if(Math.random() > PROBABILITY ){ // caso não dê, apenas descartamos :}

				// Unserialize to a RDTPacket object
				RDTPacketUDP packet = (RDTPacketUDP) SerializerUDP.toObject(receivedPacket.getData());

				//System.out.println("Packet with sequence number " + packet.getSeq() + " received (last: " + packet.isLast() + " )");

				if(packet.getSeq() == waitingFor && packet.isLast()){

					waitingFor++;
					received.add(packet);

					//System.out.println("Last packet received");

					//System.out.println(" ---------------------------- ");

					lastOne = true;

					msgByte = null;

					int msgByteLength = 0;			

					for(RDTPacketUDP p : received){

						if(msgByte != null){
							msgByteLength = msgByte.length 
									+ p.getData().length;
						}else{
							msgByteLength = p.getData().length;
						}


						byte[] result = new byte[msgByteLength];
						if(msgByte != null){
							// copy a to result
							System.arraycopy(msgByte, 0, result, 0, msgByte.length);
							// copy b to result
							System.arraycopy(p.getData(), 0, result, msgByte.length, p.getData().length);
						}else{
							result = p.getData();
						}


						msgByte = result;		


					}


					//System.out.println(" --------------------------- ");
				}else if(packet.getSeq() == waitingFor){
					waitingFor++;
					received.add(packet);
					//System.out.println("Packed stored in buffer");
				}else{
					//System.out.println("Packet discarded (not in order)");
				}

				// Create an RDTAck object
				RDTAckUDP ackObject = new RDTAckUDP(waitingFor);

				// Serialize
				byte[] ackBytes = SerializerUDP.toBytes(ackObject);
				//System.out.println("Length of ackBytes = " + ackBytes.length);
				// Send
				DatagramPacket ackPacket = new DatagramPacket(ackBytes, ackBytes.length, receivedPacket.getAddress(), receivedPacket.getPort());
				//System.out.println("Sending ACK for this port: " + receivedPacket.getPort() + " and this adress: " + receivedPacket.getAddress());


				if(Math.random()>PROBABILITY)
					fromSender.send(ackPacket);

				//System.out.println("ENVIANDO PACKET X COM LENGTH" + ackPacket.getLength());
				//System.out.println("Sending ACK to seq " + waitingFor + " with " + ackBytes.length  + " bytes");

			}

			if(lastOne == true){
				break;
			}



		}

		Message msg = (Message) SerializerUDP.toObject(msgByte);



		System.out.println("Recebimento concluido com sucesso");
		return msg;


	}



}
