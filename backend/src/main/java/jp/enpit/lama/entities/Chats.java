package jp.enpit.lama.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Chats {
	@XmlElement(name="chats")
	private List<Chat> list = new ArrayList<>();

	public Chats(){
	}
	
	public Chats(List<Chat> ChatsList){
		list.addAll(ChatsList);
	}
	
	public List<Chat> chats(){
		return Collections.unmodifiableList(list);
	}
	
	public int size(){
		return list.size();
	}
	
	public Iterator<Chat> iterator(){
		return list.iterator();
	}
	
	public Chat[] toArray(){
		return list.toArray(new Chat[size()]);
	}
}