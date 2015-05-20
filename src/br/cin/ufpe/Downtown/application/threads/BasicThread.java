package br.cin.ufpe.Downtown.application.threads;

import br.cin.ufpe.Downtown.application.utils.Logger;

public class BasicThread implements Runnable {

	private boolean running;
	
	public BasicThread(){
		this.running = true;
	}
	
	
	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		
		this.running = running;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public void stop(){
		this.running = false;
	}
	
}
