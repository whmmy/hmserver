package hm.server;

import java.io.IOException;
import java.net.Socket;

import servlet.ServLet;
import util.CloseUtil;

public class Dispatcher implements Runnable {
	//一个请求与响应 用一个Dispatcher
	private Response rep;
	private Request req;
	private Socket client;
	private int code=200;
	public Dispatcher(Socket client) {
		this.client=client;
		try {
			rep=new Response(client.getOutputStream());
			req=new Request(client.getInputStream());
		} catch (IOException e) {
			code=500;
			return ;
		}
	}
	
	@Override
	public void run() {
		
		try {
			ServLet servlet=WebApp.getServLet(req.getURL());
			if(null==servlet) {
				this.code=404;//找不到处理
			}else {
				servlet.service(req, rep);			
			}
			rep.pushToClient(code);
		} catch (Exception e1) {
			this.code=500;
		}
//		try {
//			rep.pushToClient(500);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		CloseUtil.closeSocket(client);
		
	}

}
