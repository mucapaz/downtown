package br.cin.ufpe.Downtown.application.entities;

import java.io.Serializable;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837951076955694094L;

	private String name;

	private int port;
	
	private char sex;

	private FileWrapper image;

	public User(String name, char sex, FileWrapper image) {
		this.image = image;
		this.name = name;
		this.sex = sex;
	}

	public User(String name, char sex) {
		this.name = name;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.port == ((User) obj).getPort();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public FileWrapper getImage() {
		return image;
	}

	public void setImage(FileWrapper image) {
		this.image = image;
	}
	
	
}
