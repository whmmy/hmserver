package hm.server;

import java.util.ArrayList;
import java.util.List;

public class Mapping {
	private String name;
	private List<String> urlPatterns;
	public Mapping() {
		urlPatterns=new ArrayList<String>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getUrlPatterns() {
		return urlPatterns;
	}
	public void setUrlPatterns(List<String> urlPatterns) {
		this.urlPatterns = urlPatterns;
	}
	

}
