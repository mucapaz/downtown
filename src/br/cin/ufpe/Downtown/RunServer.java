package br.cin.ufpe.Downtown;

import java.io.IOException;

import br.cin.ufpe.Downtown.application.Server;


public class RunServer {
	public static void main(String[] args) throws IOException{
		new Server(30000);
	}
}