package hm.server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import servlet.ServLet;

public class WebApp {
	private static ServletContext contxt;
	static {
		try {
			WebHandler web=new WebHandler();
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("WEB_INFO/web.xml"),web);
			
			
			
			contxt=new ServletContext();
			Map<String,String> mapping=contxt.getMapping();			
			for(Mapping map:web.getMappingList()) {
				List<String> urls=map.getUrlPatterns();
				for(String url:urls) {
					mapping.put(url,map.getName());
				}
			}			
			Map<String,String> servlet=contxt.getServlet();
			for(Entity serv:web.getServletList()) {
				servlet.put(serv.getName(),serv.getClz());
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ServLet getServLet(String URL) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if(null==URL||(URL=URL.trim()).equals("")) {
			return null;
		}
		String name= contxt.getServlet().get(contxt.getMapping().get(URL));
		return (ServLet)Class.forName(name).newInstance();
	}
}
