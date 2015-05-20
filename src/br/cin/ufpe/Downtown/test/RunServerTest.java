package br.cin.ufpe.Downtown.test;

import java.io.IOException;

import br.cin.ufpe.Downtown.application.Configuration;
import br.cin.ufpe.Downtown.application.Server;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class RunServerTest {
	
	public static int userN=0;
	
	public static void main(String[] args) {
		
		RunServerTest mainTest = new RunServerTest();
		
		Logger.info("Running server chat, no GUI implemented", mainTest);
		try {
			Server server = new Server(30000, 10000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
