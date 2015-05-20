package br.cin.ufpe.Downtown.application.socket;

import java.io.IOException;

import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.socket.udpSocketWrapper.SerializerUDP;

public class TesteSerializer {
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		
		Message msg = new Message("Stringcoloro");
		
		byte[] array = SerializerUDP.toBytes(msg);
		
		Message msgresult = (Message)SerializerUDP.toObject(array);
		
		System.out.println(msgresult.getType());
	}
}
