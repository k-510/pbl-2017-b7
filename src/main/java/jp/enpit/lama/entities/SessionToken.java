package jp.enpit.lama.entities;

import javax.xml.bind.annotation.XmlElement;

public class SessionToken {

	@XmlElement(name = "session_token")
	private String sessionToken;

	public SessionToken() {
		this("");
	}

	public SessionToken(String sessionToken) {
		setSessionToken(sessionToken);
	}

	public String getSessionToken() {
		return this.sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

}
