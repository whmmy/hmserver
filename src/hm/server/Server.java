package hm.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import util.CloseUtil;

public class Server {
	
	private ServerSocket server;
	private int port;
	
	private boolean isShutDown=false;
	
	public static void main(String[] args) {
		Server httpser=new Server(10086);
		httpser.start();
	}
	
	public Server() {
		this.port=10086;
	}
	public Server(int port) {
		this.port=port;
	}
	
	public void start() {
		try {
			server=new ServerSocket(port);
			System.out.println("server i=start"+port+"10086");
			this.receive();
		} catch (IOException e) {
			System.out.println("server shut down");
			//e.printStackTrace();
			stop();
		}
	}
	
	private void receive() {
		try {
			while(!isShutDown) {
				new Thread(new Dispatcher(server.accept())).start();				
			}
			
		} catch (IOException e) {
			System.out.println("server shut down");
			stop();
		}
	}
	
	public void stop() {
		isShutDown=true;
		CloseUtil.closeSocket(server);
	}

}
