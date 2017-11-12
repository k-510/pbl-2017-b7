package jp.enpit.lama.entities;

import javax.xml.bind.annotation.XmlElement;

import org.bson.Document;

public class Condition {
	
	@XmlElement(name = "tagID")
	private int tagID;
	@XmlElement(name = "keyword")
	private String keyword;
	
	
	public Condition(){
		tagID = 0;
		keyword = "";
	}
	public Condition(int tagID){
		this();
		settagID(tagID); 
	}
	public Condition(int tagID, String keyword){
		this(tagID);
		setkeyword(keyword);
	}
	public Condition(Document document){
		this(document.getInteger("tagID"), document.getString("keyword"));
	}
	
	public void setCondition(Condition condition){
		settagID(condition.tagID());
		setkeyword(condition.keyword());
	}
	public void settagID(int tagID){
		this.tagID = tagID;
	}
	public int tagID(){
		return tagID;
	}
	
	public void setkeyword(String keyword){
		this.keyword = keyword;
	}
	public String keyword(){
		return keyword;
	}
}
