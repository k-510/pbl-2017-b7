package jp.enpit.lama.entities;

import javax.xml.bind.annotation.XmlElement;

public class User {
	@XmlElement(name = "userID")
	private int userID;
	@XmlElement(name = "session")
	private String session;
	private String userName;
	private String hashedPassword;
	
	public User(){
		setuserID(-1);
		setsession("");
		sethashedPassword("");
	}
	public User(String userName, int userID, String hashedPassword, String session){
		this();
		setuserID(userID);
		setsession(session);
		sethashedPassword(hashedPassword);
	}
	
	public String userName(){
		return userName;
	}
	public void setuserName(String userName){
		this.userName = userName;
	}
	
	public int userID(){
		return userID;
	}
	public void setuserID(int userID){
		this.userID = userID;
	}
	
	public String hashedPassword(){
		return hashedPassword;
	}
	public void sethashedPassword(String hashedPassword){
		this.hashedPassword = hashedPassword;
	}
	
	public String session(){
		return session;
	}
	public void setsession(String session){
		this.session = session;
	}
}
