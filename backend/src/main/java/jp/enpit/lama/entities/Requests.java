package jp.enpit.lama.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Requests {
	@XmlElement(name="requests")
	private List<Request> list = new ArrayList<>();

	public Requests(){
	}
	
	public Requests(List<Request> RequestsList){
		list.addAll(RequestsList);
	}
	
	public List<Request> requests(){
		return Collections.unmodifiableList(list);
	}
	
	public int size(){
		return list.size();
	}
	
	public Iterator<Request> iterator(){
		return list.iterator();
	}
	
	public Request[] toArray(){
		return list.toArray(new Request[size()]);
	}
}