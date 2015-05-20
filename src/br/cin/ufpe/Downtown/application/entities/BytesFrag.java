package br.cin.ufpe.Downtown.application.entities;

import java.io.Serializable;

public class BytesFrag implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8620350398080322033L;

	private byte[] bytes;
	
	private int fileID;
	
	private int index;
	
	private int length;
	
	public int getFileID() {
		return fileID;
	}

	public void setID(int fileID) {
		this.fileID = fileID;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public BytesFrag(byte[] bytes, int fileID, int index) {
		super();
		this.bytes = bytes;
		this.fileID = fileID;
		this.index = index;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}



	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	
	
	
	
}
