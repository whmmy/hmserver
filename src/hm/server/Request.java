package hm.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Request {
	private static final String CRLF="\r\n";
	private String requestInfo;
	private InputStream is;
	private String method;//请求方式
	private String URL;//请求资源
	private Map<String,ArrayList<String>> parameterMapValue;//键值对Map
	private String paramString;
	
	public Request() {
		this.method="";
		this.URL="";
		this.requestInfo="";
		this.parameterMapValue=new HashMap<String, ArrayList<String>>();
		this.paramString="";
	}
	
	public Request(InputStream is) {
		this();
		this.is=is;
		byte[] bty=new byte[20480];
		int len;
		try {
			len = is.read(bty);
			requestInfo=new String(bty, 0, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("读取请求头数据失败！");
			return;
		}
		parseRequestInofo();
	}
	
	public Request(Socket socket) throws IOException {
		this(socket.getInputStream());
	}
	/*
	 * 
	 * 分析头文件
	 * GET /indexhtml?un=123&pw=123 HTTP/1.1
		Host: localhost:10086
		Connection: keep-alive
		Upgrade-Insecure-Requests: 1
		User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36
		Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*//*;q=0.8
		Accept-Language: zh-CN,zh;q=0.8
	 * 
	 */
	
	private void parseRequestInofo() {
		if(requestInfo==null||requestInfo.equals("")) {
			return;
		}		
		//分析 数据
		String line=requestInfo.substring(0, requestInfo.indexOf(CRLF));
		int idx=line.indexOf("/");// /position
		method=line.substring(0,idx).trim().toUpperCase();
		String urlString=line.substring(idx, line.indexOf("HTTP/")).trim();
		switch(method) {
			case "GET":
				methodGet(urlString);
				break;
			case "POST":
				methodPost(urlString);
				break;
		}
		//System.out.println(requestInfo);		
		if(paramString==null||paramString.equals("")) {
			return;
		}
		parseParams();
	}
	
	private void parseParams() {
		StringTokenizer token=new StringTokenizer(paramString, "&");
		while(token.hasMoreTokens()) {
			String KeyValue=token.nextToken();
			String[] KeyValues=KeyValue.split("=");
			if(KeyValues.length==1) {
				KeyValues=Arrays.copyOf(KeyValues, 2);
				KeyValues[1]=null;
			}
			String Key=KeyValues[0].trim();
			String Value=
					null==KeyValues[1]?null:decode(KeyValues[1].trim(),"utf-8");
			//转换成Map
			if(!parameterMapValue.containsKey(Key)) {
				parameterMapValue.put(Key, new ArrayList<String>());				
			}
			List<String> values=parameterMapValue.get(Key);
			values.add(Value);
		}
	}
	
	private void methodPost(String urlString) {
		this.URL=urlString;
		this.paramString=requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
	}
	private void methodGet(String urlString) {
		if(urlString.contains("?")) {
			String[] urlArray=urlString.split("\\?");
			this.URL=urlArray[0];
			this.paramString=urlArray[1];
			
		}else {
			this.URL=urlString;
		}
	}
	//转换编码格式
	private String decode(String value,String code) {
		try {
			return java.net.URLDecoder.decode(value,code);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//根据Name获取多个值
	public String[] getParametrValues(String name) {
		List<String> values=null;
		if(null!=(values=parameterMapValue.get(name))) {
			return values.toArray(new String[0]);
		}
		return null;
	}

	//根据Name获取单个值
	public String getParametrValue(String name) {
		List<String> values=null;
		if(null!=(values=parameterMapValue.get(name))) {
			return values.get(0);
		}
		return null;
	}

	public String getRequestInfo() {
		return requestInfo;
	}

	public String getMethod() {
		return method;
	}

	public String getURL() {
		return URL;
	}
		
	public String getKeyValues() {
		return parameterMapValue.entrySet().toString();
	}

}
