package hm.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebHandler extends DefaultHandler {
	private List<Entity> servletList;
	private List<Mapping> mappingList;
	private Entity servlet;
	private Mapping mapping;
	private boolean isMap;
	private String begTag;
	@Override
	public void startDocument() throws SAXException {
		//开始读取文件，初始化列表
		servletList=new ArrayList<Entity>();
		mappingList=new ArrayList<Mapping>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//元素开始
		if(null!=qName) {
			begTag=qName;
			if(qName.equals("servlet")) {
				servlet=new Entity();
				isMap=false;
			}else if(qName.equals("servlet-mapping")) {
				mapping=new Mapping();
				isMap=true;
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		//分析valuse
		if(null!=begTag) {
			String value=new String(ch,start,length);
			if(!isMap) {
				if(begTag.equals("servlet-name")) {
					servlet.setName(value);
				}else if(begTag.equals("servlet-class")) {
					servlet.setClz(value);					
				}				
			}else {
				if(begTag.equals("servlet-name")) {
					mapping.setName(value);
				}else if(begTag.equals("url-pattern")) {
					mapping.getUrlPatterns().add(value);
				}		
			}
			
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		//元素结束
		if(null!=qName) {			
			if(qName.equals("servlet")) {
				servletList.add(servlet);
			}else if(qName.equals("servlet-mapping")) {
				mappingList.add(mapping);
			}
		}
		begTag=null;
	}

	@Override
	public void endDocument() throws SAXException {
		//文件解析结束
	}

	public List<Entity> getServletList() {
		return servletList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}
	
//	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//		WebHandler web=new WebHandler();
//		SAXParserFactory factory=SAXParserFactory.newInstance();
//		SAXParser parser=factory.newSAXParser();
//		parser.parse(Thread.currentThread().getContextClassLoader()
//				.getResourceAsStream("WEB_INFO/web.xml"),web);
//		System.out.println(web.getServletList());
//	}

	

}
