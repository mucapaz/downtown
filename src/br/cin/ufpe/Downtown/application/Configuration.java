package br.cin.ufpe.Downtown.application;

import java.util.Vector;

public class Configuration {
	
	public static final String PROTOCOL = "TCP";
	public static final boolean LOGGER = true;
	public static final int FILTER_LOGGER = 0; // 0 = no filter, 1 = class name 
	public static Vector<String> FILTER_CLASSES = new Vector<String>(); 
	public static final int FRAG_BYTES = 5; // each PACKET can carry 200 bytes 
	
	public static boolean isLogger(){
		return Configuration.LOGGER;
	}
	
	public static boolean isTCP(){
		return PROTOCOL.equals("TCP");
	}

}
