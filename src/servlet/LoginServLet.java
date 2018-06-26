package servlet;

import hm.server.Request;
import hm.server.Response;

public class LoginServLet extends ServLet{

	@Override
	public void doPost(Request req,Response rep) throws Exception {
		String msg="";
		if(isUser(req.getParametrValue("username"), req.getParametrValue("userpsw"))) {
			msg="success";
		}else {
			msg="errol";
		}
		rep.println("<html><head><title>Whm's server response</title></head>");
		rep.println("<body>");
		rep.println("welcome to my server").println(msg);
		rep.println("</body></html>");
		
	}
	
	public boolean isUser(String name,String pwd) {
		return name.equals("whmmy")&&pwd.equals("123456");
	}

	@Override
	public void doGet(Request req,Response rep) throws Exception {
		
	}

}
