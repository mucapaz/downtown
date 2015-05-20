package br.cin.ufpe.Downtown.application.entities;

import java.io.Serializable;

public class UserListEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1218953100862578370L;

	private User user;
	
	private String ip;
	
	private int port;

	public UserListEntry(User user, String ip, int port) {
		this.user = user;
		this.ip = ip;
		this.port = port;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString(){
		return this.user.getName() + "@" + this.ip + ":" + this.port;
	}

	@Override
	public boolean equals(Object obj) {
		return this.port== ((UserListEntry) obj).getPort();
	}
	
}

