package br.cin.ufpe.Downtown.application.socket.udpSocketWrapper;
import java.io.Serializable;


public class RDTAckUDP implements Serializable{
	
	private int packet;

	public RDTAckUDP(int packet) {
		super();
		this.packet = packet;
	}

	public int getPacket() {
		return packet;
	}

	public void setPacket(int packet) {
		this.packet = packet;
	}
	
	

}
