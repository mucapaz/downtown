package br.cin.ufpe.Downtown.application.entities;

import java.io.Serializable;
import java.util.Random;

public class Message implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7244884465891374433L;

	private String type;

	private int id;
	
	private Object data;

	public Message(String type, Object data) {
		this.type = type;
		this.data = data;
		this.id = new Random().nextInt(999999);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Message(String type) {
		this.type = type;
	}

	
	/**
	 * <ul>
	 * 	<li>"USER_INFO": Object User 
	 * 	<li>"CLOSE_CONNECTION": int port
	 *  <li>"USER_LIST"
	 * </ul>
	 * 
	 * 
	 * */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		
		return "TYPE: " + this.type + " -  DATA: " +  this.data;
	}
	
}
