package br.cin.ufpe.Downtown.application.entities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.sql.rowset.spi.SyncResolver;

public class FileWrapper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9077893911709826148L;

	private byte[] fileByte;
	
	private String fileName;
	
	private int count;
	
	public FileWrapper(byte[] fileByte, String fileName, int count){
		this.fileByte = fileByte;
		this.fileName = fileName;
		this.count = count;
	}

	
	/**Receives a directory to store the file*/
	public void toFile(String dir) throws IOException{
		FileOutputStream fos = new FileOutputStream(dir);
		fos.write(this.fileByte);
		fos.close();
	}
	
	
	/**Returns an byte array from a file on directory:   append(dir, name)
	 * @throws IOException */
	public static FileWrapper fromFile(String dir, String name) throws IOException{
	
		dir += (dir.lastIndexOf('/') != dir.length() -1 ) ? "/" : "";		
		dir += name; 
		File file = new File(dir); 
	    // Get the size of the file
	    long length = file.length();
	    byte[] bytes = new byte[(int) length];
	    FileInputStream fis = new FileInputStream(file);
	    BufferedInputStream bis = new BufferedInputStream(fis);	    
	    int count = bis.read(bytes);
	    fis.close();
	    bis.close();
	    return new FileWrapper(bytes, name, count);
	}
	
	
	
	public byte[] getFileByte() {
		return fileByte;
	}

	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	@Override
	public String toString() {
		return "File Name: "  + this.fileName + " - "  + "Bytes count: " + this.count;
	}
	
}

