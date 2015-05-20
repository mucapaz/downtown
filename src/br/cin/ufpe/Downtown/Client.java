package br.cin.ufpe.Downtown;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
	    Socket socket = null;
	    String host = "localhost";
	
	    socket = new Socket(host, 20000);
	
	    File file = new File("teste.jpg");
	    // Get the size of the file
	    long length = file.length();
	    if (length > Integer.MAX_VALUE) {
	        System.out.println("File is too large.");
	    }
	    byte[] bytes = new byte[(int) length];
	    FileInputStream fis = new FileInputStream(file);
	    BufferedInputStream bis = new BufferedInputStream(fis);
	    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
	
	    int count;
	
	    while ((count = bis.read(bytes)) > 0) {
	    	System.out.println("count: " + count);
	        out.write(bytes, 0, count);
	    }
	
	    out.flush();
	    out.close();
	    fis.close();
	    bis.close();
	    socket.close();
	}
}