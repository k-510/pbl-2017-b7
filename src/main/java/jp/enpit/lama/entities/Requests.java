package jp.enpit.lama.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Requests {
	@XmlElement(name = "request")
	private List<Request> list = new ArrayList<>();
	
	public Requests(){
	}
	
	public Requests(List<Request> RequestList){
		list.addAll(RequestList);
	}
	
	public List<Request> request(){
		return Collections.unmodifiableList(list);
	}
	
	public int size(){
		return list.size();
	}
	
	public Iterator<Request> iterator(){
		return list.iterator();
	}
	
	public Requests[] toArray(){
		return list.toArray(new Requests[size()]);
	}
	
}
