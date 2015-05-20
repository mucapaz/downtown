package br.cin.ufpe.Downtown;


import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

import br.cin.ufpe.Downtown.gui.Main;



public class RunChat {
	
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
