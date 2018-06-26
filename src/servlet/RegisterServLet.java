package servlet;

import hm.server.Request;
import hm.server.Response;

public class RegisterServLet extends ServLet{

	@Override
	public void doPost(Request req,Response rep) throws Exception {
		
	}

	@Override
	public void doGet(Request req,Response rep) throws Exception {
		rep.println("<html><head><title>register</title></head>");
		rep.println("<body>");
		rep.println("welcome to my server ").println(req.getParametrValue("username"));
		rep.println("</body></html>");
		
	}

}
