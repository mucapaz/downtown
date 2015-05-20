package br.cin.ufpe.Downtown.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

import javax.sql.rowset.spi.SyncResolver;

import br.cin.ufpe.Downtown.application.Chat;
import br.cin.ufpe.Downtown.application.Configuration;
import br.cin.ufpe.Downtown.application.P2PConnection;
import br.cin.ufpe.Downtown.application.entities.FileWrapper;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class RunChatTest {
	
	public static boolean LOCAL_FILE = true;
	
	private Chat chat;
	
	public static Scanner in = new Scanner(System.in);
	
	public RunChatTest() {
		synchronized (this) {
			System.out.println("Nome: ");
			
			String name;
			//name = in.next();
			name = "Marlon";
			
			
			User user = new User(name, 'm');
			System.out.println("User created > " + user);
			
			
			System.out.println("Running user chat, no GUI implemented");
			try {
				System.out.println("Creating chat");
				this.chat =  new Chat("localhost", 30000, user);
				System.out.println("Chat created");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("IO Exception while trying to init CHAT");
			}			
		}

	}

	
	
	
	public void printOnlineUsers(){
		synchronized (this) {
			System.out.println("------------- ONLINE USERS -----------");
			for(int i=0; i < chat.getOnlineUsers().size(); i++){
				System.out.println(i + " -  " + chat.getOnlineUsers().get(i).getUser().getName() + " - " + chat.getOnlineUsers().get(i).getIp() + ": PORT: " + chat.getOnlineUsers().get(i).getPort());
			}		
		}	
	}
	
	public void printConnectedUsers(){
		P2PConnection p2pConn;
		Vector<P2PConnection> vecP2PConn;
		boolean oneAuth = false;
		synchronized (this) {
			System.out.println("-------- P2PConnections auth with you ---------");
			if(chat.getP2pConnections().isEmpty()) {
				System.out.println("0 on P2PConnections list");
			}
			else {
				vecP2PConn =  chat.getP2pConnections();
				for(int i=0; i < vecP2PConn.size(); i++){
					p2pConn = vecP2PConn.get(i);
					if(p2pConn.isAuthenticated()) {
						oneAuth = true;
						System.out.println("i="+i + " - User: " + p2pConn.getUser().getName());
					}
				}
				if(!oneAuth ) {
					System.out.println("0 Users auth with you");
				}
			}
			
		}

		
	}
	
	public void connectUser(int i){
		synchronized (this) {
			
			try {
				User user = chat.getOnlineUsers().get(i).getUser();
				if ( chat.findP2PConnection(user) != null && chat.findP2PConnection(user).isAuthenticated()) {
					System.out.println("Already connected with User: " + user);
				}
				else {
					System.out.println("Trying to connect to user with index = " + i + " [size: " + chat.getOnlineUsers().size()+"] ");
					chat.connectToClient(chat.getOnlineUsers().get(i).getIp(), chat.getOnlineUsers().get(i).getPort());
					System.out.println("Conecton established");
				}
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.println("Invalid position");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Unhknow host while trying to Connect user");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("IOException host while trying to connect user");
			}


		}
	}
	
	
	
	public void sendMessage(int i, int type){

		synchronized (this) {
			try {
				P2PConnection p2pConn;
				
				
	 			p2pConn = chat.getP2pConnections().get(i);
	 			
	 			
	 			if(!p2pConn.isAuthenticated()) {
	 				System.out.println("You've not auth with this user yet ! ");
	 			}
	 			
	 			if(type==0) { // texto
		 			String message;
		 			System.out.println("Enter message text:");
		 			message = in.next();
		 			p2pConn.getToClientMessages().add(new Message("TEXT", message));
	 			}
	 			else if(type ==1 ){
	 				
	 				String name;
	 				String dir;
	 				
	 				if(RunChatTest.LOCAL_FILE) dir = "";
	 				else {
	 					//System.out.println("Enter with file directory: "); 
	 				//	dir = in.next();
	 				}
	 				
	 				
	 			//	System.out.println("Entre com o nome do arquivo");
	 			//	name = in.next(); // nome do arquivo
	 				
	 				FileWrapper fileWrapper = FileWrapper.fromFile("", "teste.txt");
	 				

	 				
	 				p2pConn.getToClientMessages().add(new Message("FILE", fileWrapper));

	 				
	 			}
	 		

	 			System.out.println("Message added to Buffer");
	 			
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid index [i: "+ i +"] but Size: " + chat.getP2pConnections().size());
			} catch (IOException e) {
				
				//e.printStackTrace();
				System.out.println("Arquivo nao encontrado");
			}

			
			
		}
		
		
	}

	public void clearWindow(){
		for(int i =0; i< 1000; i++ ) System.out.println("");
	}
	
	
/*	public static void main(String[] args) {
		
		try {
			Chat c = new Chat("localhost", 30000, new User("Marlon", 'f'));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	public static void main(String[] args) {
			
		Configuration.FILTER_CLASSES.add("P2PMessageHandler");
		
		
		
			

		
		RunChatTest chatTest = new RunChatTest();
		
		while(true ) {
			
			chatTest.printOnlineUsers();
			
			chatTest.printConnectedUsers();
			
			int opt = 3;
			do {
				System.out.println("-- Try one option: "
						+ "\n0 - connect user"
						+ "\n1 - send message"
						+ "\n2 - refresh lists"
						+ "\n3 - clear window"
						+ "\n4 - send file" ) ;
				opt = chatTest.in.nextInt();
								
			} while( opt < 0 || opt > 4);
			
			if( opt == 2 ) {
				System.out.println(" -- Refreshing -- ");
				continue;
			}
			if(opt==3 ) {
				chatTest.clearWindow();
				continue;
			}
			
			int id;
			System.out.println("Enter id: ");
			id = chatTest.in.nextInt();
			if(opt==0)
				chatTest.connectUser(id);
			if(opt==1)
				chatTest.sendMessage(id,0);
			if(opt==4)
				chatTest.sendMessage(id, 1);

			
			
		}
		
		
	}
	
	
}
