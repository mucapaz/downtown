package br.cin.ufpe.Downtown.application.utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.cin.ufpe.Downtown.application.Configuration;


public class Logger {
	
	
	
	// TODO: Find a better place for this
	private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
	
	public static boolean canLog(String type){
		
		if(Configuration.FILTER_LOGGER==1) {
			synchronized (Configuration.FILTER_CLASSES) {
				for(int i=0; i < Configuration.FILTER_CLASSES.size(); i++){
					//System.out.println("Comparing: " + Configuration.FILTER_CLASSES.get(i) +" with " + type +  " awnser: " +Configuration.FILTER_CLASSES.get(i).equals(type)  );
					
//					System.out.println("Comparing: ["+Configuration.FILTER_CLASSES.get(i)  +" ]with[ " + type+"] result:" +Configuration.FILTER_CLASSES.get(i).equals(type));
					
					if( Configuration.FILTER_CLASSES.get(i).equals(type)) {
						return true;
					}
				}			
			}			
		}
		
		

		return false;
	}
	
	public static void log(String message, Object object){

//		if(Configuration.isLogger()) {
//
//			if(Configuration.FILTER_LOGGER != 0 )  {
//				
//				if(canLog(object.getClass().getSimpleName())) {
//					
//					System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) +" [LOG] " + message) ;
//				}
//			}
//			else {
//				System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) +" [LOG] " + message) ;
//			}
//		}
	}


	
	
	public static void info(String message, Object object){

//		if(Configuration.isLogger()) {
//			if(Configuration.FILTER_LOGGER != 0 )  {
//				if(canLog(object.getClass().getSimpleName())) {
//					System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [INFO] " + message);
//				}
//			}
//			else {
//					System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [INFO] " + message);				
//			}
//		}	
	}
	
	public static void error(String message, Object object){

		
//		if(Configuration.isLogger()) {
//			if(Configuration.FILTER_LOGGER != 0 )  {
//				if(canLog(object.getClass().getSimpleName())) {
//					System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [ERROR] " + message);
//				}
//			}
//			else {
//				System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [ERROR] " + message);				
//			}
//		}	
			
	}
	
	public static void custom(String label, String message, Object object){
//		if(Configuration.isLogger()) {
//			if(Configuration.FILTER_LOGGER != 0 )  {
//				if(canLog(object.getClass().getSimpleName())) {
//					System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [" + label + "] " + message);
//				}
//			}
//			else {
//				System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [" + label + "] " + message);				
//			}
//		}			
	}
	
	public static void fatal(String message, Object object){
		
//		if(Configuration.isLogger()) {
//			if(Configuration.FILTER_LOGGER != 0 )  {
//				if(canLog(object.getClass().getSimpleName())) {
//					System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [FATAL] " + message);
//				}
//			}
//			else {
//				System.out.println(object.getClass().getSimpleName() + " > "  +formatter.format(new Date()) + " [FATAL] " + message);				
//			}
//		}				
	}
	

	
}
