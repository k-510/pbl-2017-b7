package jp.enpit.lama.entities;

import javax.xml.bind.annotation.XmlElement;

public class User {
	@XmlElement(name = "userID")
	private int userID;
	@XmlElement(name = "session")
	private String session;
	
	
	public User(){
		setuserID(-1);
		setsession("");
	}
	public User(int id, String session){
		this();
		setuserID(id);
		setsession(session);
	}
	
	public int userID(){
		return userID;
	}
	public void setuserID(int userID){
		this.userID = userID;
	}
	
	public String session(){
		return session;
	}
	public void setsession(String session){
		this.session = session;
	}
}
