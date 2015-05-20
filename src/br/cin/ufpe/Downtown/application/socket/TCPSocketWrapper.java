package br.cin.ufpe.Downtown.application.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import br.cin.ufpe.Downtown.application.entities.Message;

public class TCPSocketWrapper implements SocketWrapper {

	private Socket socket;
	
	private ObjectOutputStream objectOutputStream;
	
	private ObjectInputStream objectInputStream;
	
	public TCPSocketWrapper(Socket socket) throws IOException {
		this.socket = socket;
		this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		this.objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
		this.objectOutputStream = objectOutputStream;
	}

	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}

	public void setObjectInputStream(ObjectInputStream objectInputStream) {
		this.objectInputStream = objectInputStream;
	}

	@Override
	public void sendMessage(Message message) throws IOException {
		this.objectOutputStream.writeObject(message);
	}

	@Override
	public Message receiveMessage() throws ClassNotFoundException, IOException {
		Object obj = this.objectInputStream.readObject();
		return (Message) obj;
	}

	@Override
	public void close() throws IOException {
		this.objectInputStream.close();
		this.objectOutputStream.close();
		this.socket.close();
	}

	@Override
	public String getIP() {
		return this.socket.getInetAddress().getHostAddress();
	}

	@Override
	public boolean isSocketed() {

		return true;
	}
	
}
