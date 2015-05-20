package br.cin.ufpe.Downtown.application.socket.udpSocketWrapper;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.Vector;

import br.cin.ufpe.Downtown.Exceptionable;
import br.cin.ufpe.Downtown.application.entities.Message;
import br.cin.ufpe.Downtown.application.entities.User;
import br.cin.ufpe.Downtown.application.socket.UDPSocketWrapper;
import br.cin.ufpe.Downtown.application.threads.Sender;
import br.cin.ufpe.Downtown.application.utils.Logger;

public class UDPStarter implements Exceptionable {

	
	UDPSocketWrapper udpServerAccept;
	
	private int minhaMira;
	private int mySender; 
	private int myReceiverSocketNumber;
	private int p2pPortUser;
	
	
	public UDPStarter(int minhaMira, int mySender, int myReceiverSocketNumber, boolean start) throws SocketException{
		this.minhaMira = minhaMira;
		this.mySender = mySender;
		this.myReceiverSocketNumber = myReceiverSocketNumber;
		if(start) {
			System.out.println("UDP Started : \n"
					+ ""
					+ "minhaMira: " + minhaMira+"\n"
					+ "mySender: " + mySender  +"\n"
					+ "myReceiverSocketNumber: " + myReceiverSocketNumber+"\n"
					+ ""
					+ ""
					+ " ");
			
			
			udpServerAccept = new UDPSocketWrapper("localhost", 0, mySender, myReceiverSocketNumber, 0);
		}
		
	}
	
	public void initSocket() throws SocketException{
		this.udpServerAccept = new UDPSocketWrapper("localhost", 0, mySender, myReceiverSocketNumber, 0);
	}
	
	
	public UDPStarter(String type) throws SocketException{
		if(type.equals("SERVER")) {
			minhaMira = 43000;
			 mySender = 44000; 
			 myReceiverSocketNumber = 30000;
			 p2pPortUser = 32000;
			 udpServerAccept = new UDPSocketWrapper("localhost", minhaMira, mySender, myReceiverSocketNumber, 0);
		}

		 
	}

	/**To connect into a server*/
	public synchronized UDPSocketWrapper accept(int minhaMira, int mySender, int myReceiverSocketNumber, int extra) throws Exception{
		//int minhaMira;
	//	int mySender = 45000; 
		//int myReceiverSocketNumber = 30000;
		
		UDPSocketWrapper udpServer;
		
		Scanner in = new Scanner(System.in);
		
		Message m;
		System.out.println("Recebendo mensagem 1");
		m = udpServerAccept.receiveMessage();
		System.out.println("Mensagem recebida 1 --> "  + m);
		String strData = (String) m.getData();
		
		String ip = strData.substring(0, strData.indexOf('@'));
		int auxMira = Integer.parseInt( strData.substring(strData.indexOf('@')+1));
		
		mySender++;
		myReceiverSocketNumber++;
		udpServer = new UDPSocketWrapper(ip, auxMira , mySender, 0, 0);
		minhaMira++;
		p2pPortUser++;
		m = new Message("SERVER_RESPONSE", ""+minhaMira+"@"+myReceiverSocketNumber+"@"+(p2pPortUser));
		
		System.out.println("mira: " + auxMira + "\nmySender"+mySender + "\nMyReceive:"+myReceiverSocketNumber);
		
		System.out.println("Enviando response 2 ");
		
		udpServer.sendMessage(m);
		
		System.out.println("Response enviada 2 ");
		
		
		
	
		
		
		
		System.out.println("Criando novo socket pro usuário: ");
		mySender++;
		udpServer = new UDPSocketWrapper(ip, minhaMira, mySender, myReceiverSocketNumber, 0);
		
		
		System.out.println("minhaMira: " + minhaMira + "\nminhaSender: " + mySender +" minhaEntrada: " + myReceiverSocketNumber+"\n");
		
		System.out.println("Novo socket criado");
		
		
		//System.out.println("Digite alguma coisa 1 ");in.next();
		
		return udpServer;

	}
	
	
	/**To connect into a server*/
	public synchronized UDPSocketWrapper accept(int extra) throws Exception{
		//int minhaMira;
	//	int mySender = 45000; 
		//int myReceiverSocketNumber = 30000;
		
		UDPSocketWrapper udpServer;
		
		Scanner in = new Scanner(System.in);
		
		Message m;
		System.out.println("Recebendo mensagem 1");
		m = udpServerAccept.receiveMessage();
		System.out.println("Mensagem recebida 1 --> "  + m);
		String strData = (String) m.getData();
		
		String ip = strData.substring(0, strData.indexOf('@'));
		int auxMira = Integer.parseInt( strData.substring(strData.indexOf('@')+1));
		
		mySender++;
		myReceiverSocketNumber++;
		udpServer = new UDPSocketWrapper(ip, auxMira , mySender, 0, 0);
		minhaMira++;
		p2pPortUser++;
		m = new Message("SERVER_RESPONSE", ""+minhaMira+"@"+myReceiverSocketNumber+"@"+(p2pPortUser));
		
		System.out.println("mira: " + auxMira + "\nmySender"+mySender + "\nMyReceive:"+myReceiverSocketNumber);
		
		System.out.println("Enviando response 2 ");
		
		udpServer.sendMessage(m);
		
		System.out.println("Response enviada 2 ");
		
		
		
	
		
		
		
		System.out.println("Criando novo socket pro usuário: ");
		mySender++;
		udpServer = new UDPSocketWrapper(ip, minhaMira, mySender, myReceiverSocketNumber, 0);
		
		
		System.out.println("minhaMira: " + minhaMira + "\nminhaSender: " + mySender +" minhaEntrada: " + myReceiverSocketNumber+"\n");
		
		System.out.println("Novo socket criado");
		
		
		//System.out.println("Digite alguma coisa 1 ");in.next();
		
		return udpServer;

	}
	
	
	public synchronized static UDPSocketWrapper connect(String serverIP, int mira, int envio, int receber, Exceptionable exceptionable) throws Exception{
		
		Logger.info("Trying connect into: "
				+ "Server IP: " + serverIP + "\n"
				+ "mirando em: " + mira +"\n"
				+ "envio: " + envio + "\n"
				+ "receber: " + receber + "\n"
				+ "", new UDPStarter(""));
		
		
		
		
		
		Message m ;
	
		//int minhaEnvio = envio;
		//mira  = 30.000
		//receber = 20.000 (server)
		//envio = 45 mil padrão
		UDPSocketWrapper udp = new UDPSocketWrapper(serverIP, mira, envio, receber, 0);
		
		
		String ip = InetAddress.getLocalHost().toString();
		
		ip = ip.substring(ip.indexOf('/')+1);
		
		System.out.println("Mensagem enviando 1");
		udp.sendMessage( new Message("USER_REQUEST", ip+"@"+(receber)));
		System.out.println("Mensagem enviada 1");
		
		
	
		
		System.out.println("Esperando resposta 2");
		m = udp.receiveMessage();
		System.out.println("Mensagem recebida 2 --> " + m);
		
		String strData = (String) m.getData();
		
		int minhaEntrada = Integer.parseInt(strData.substring(0, strData.indexOf('@')));
		int minhaMiraServer = Integer.parseInt( strData.substring( strData.indexOf('@')+1, strData.lastIndexOf('@'))  );
		//int minhaP2PPorta = Integer.parseInt(strData.substring(strData.lastIndexOf('@')+1));
		envio++;
		
		
		
		
		System.out.println("minhaMira: "+ minhaMiraServer + "\nMinha envio:"+envio+"\nminhaEntrada: " + minhaEntrada+"\n");
		
		System.out.println("Criando sockket com server: ");
		udp = new UDPSocketWrapper(serverIP, minhaMiraServer, envio, minhaEntrada, 0);
		System.out.println("Socket criado");
		
		return udp;
		
		//System.out.println("Digite alguma coisa 1 ");in.next();
		

		
	//	Vector<Message> svector = new Vector<>();
	//	Vector<Message> rvector = new Vector<>();
		
	//	Sender s = new Sender(svector, udp, exceptionable);
		//Receiver r = new Receiver(rvector, udpServer, this);
		//Thread t1 = new Thread(s);
	//	t1.start();
		//Thread t2 = new Thread(r);
		
//		int k;
//		while(true) {
//			System.out.println("0-envia\n1-recebe");
//			k = in.nextInt();
//			if(k==0) svector.add(new Message("Teste", "msg teste"));
//						if(k==1) {
//				if(!rvector.isEmpty()) {
//					System.out.println("Recebido: " + rvector.remove(0));
//				}
//				else {
//					System.out.println("Sem mensagem");
//					
//				}
//			}
//		}
		
	}

	@Override
	public void handleExceptionUser(User user) {
		
	}

	public int getMinhaMira() {
		return minhaMira;
	}

	public void setMinhaMira(int minhaMira) {
		this.minhaMira = minhaMira;
	}

	public int getMyReceiverSocketNumber() {
		return myReceiverSocketNumber;
	}

	public void setMyReceiverSocketNumber(int myReceiverSocketNumber) {
		this.myReceiverSocketNumber = myReceiverSocketNumber;
	}
	
}

