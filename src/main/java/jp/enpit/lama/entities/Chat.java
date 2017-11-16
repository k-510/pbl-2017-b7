package jp.enpit.lama.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Chat {

	@XmlElement(name = "requestID")
	private int requestID;
	@XmlElement(name = "user_id")
	private int userID;
	@XmlElement(name = "message")
	private String message;

	public Chat() {
	}

	public Chat(int requestID) {
		this();
		setRequestID(requestID);
	}

	public Chat(int requestID, int userID) {
		this(requestID);
		setUserID(userID);
	}

	public Chat(int requestID, int userID, String message) {
		this(requestID, userID);
		setMessage(message);
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int requestID() {
		return requestID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int userID() {
		return userID;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String message() {
		return message;
	}

}
