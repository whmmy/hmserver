package servlet;

import hm.server.Request;
import hm.server.Response;

public abstract class ServLet {
	
	
	public  void service(Request req,Response rep) throws Exception {
		this.doPost(req,rep);
		this.doGet(req,rep);
	}
	
	protected abstract void doPost(Request req,Response rep) throws Exception;
	protected abstract void doGet(Request req,Response rep) throws Exception;
}
