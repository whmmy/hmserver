package hm.server;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import util.CloseUtil;



public class Response {
	private static final String CRLF="\r\n";
	private static final String BLANK=" ";
	private BufferedWriter bw;
	//正文
	private StringBuilder content;
	//头信息
	private StringBuilder headInfo;
	
	private int len;
	
	public Response() {
		content=new StringBuilder();
		headInfo=new StringBuilder();
		len=0;
	}

	public Response(OutputStream os) {
		this();
		bw=new BufferedWriter(
				new OutputStreamWriter(os)
				);
	}
	public Response(Socket socket) {
		this();
		try {
			bw=new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())
					);
		} catch (IOException e) {
			e.printStackTrace();
			headInfo=null;
		}
	}
	
	public Response print(String info) {
		content.append(info);
		len+=info.getBytes().length;
		return this;
	}
	
	public Response println(String info) {
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
	
	private void createHeadInfo(int code) {
		//HTTP协议版本，添加状态码
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch(code){
			case 200:
				headInfo.append("OK");
				break;
			case 404:
				headInfo.append("NOT FOUND");
				break;
			case 500:
				headInfo.append("SERVER ERROR");
				break;
		}
		headInfo.append(CRLF);
		
		//添加相应头
		headInfo.append("Server:Whm's Server/0.0.1").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Content-type: text/html;charset=GBK").append(CRLF);
		headInfo.append("Content-Length:").append(BLANK).append(len).append(CRLF);
		headInfo.append(CRLF);
	}
	
	public void pushToClient(int code) throws IOException {
		createHeadInfo(code);
		bw.append(headInfo);
		bw.append(content);
		bw.flush();
	}
	
	public void close() {
		CloseUtil.closeIO(bw);
	}
	

}
