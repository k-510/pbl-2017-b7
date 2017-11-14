package jp.enpit.lama.entities;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

import org.bson.Document;

public class Condition {
	
	@XmlElement(name = "tagID")
	private ArrayList<Integer> tagID;
	@XmlElement(name = "keyword")
	private String keyword;
	
	
	public Condition(){
		tagID = new ArrayList<Integer>();
		keyword = "";
	}
	public Condition(ArrayList<Integer> tagID){
		this();
		settagID(tagID); 
	}
	public Condition(ArrayList<Integer> tagID, String keyword){
		this(tagID);
		setkeyword(keyword);
	}
	public Condition(Document document){
		ArrayList<Integer> tid = (ArrayList<Integer>)document.get("tagID");
		String kwd = document.getString("keyword");
		settagID(tid);
		setkeyword(kwd);
	}
	
	public void setCondition(Condition condition){
		settagID(condition.tagID());
		setkeyword(condition.keyword());
	}
	public void settagID(ArrayList<Integer> tagID){
		this.tagID = (ArrayList<Integer>) tagID.clone();
	}
	public ArrayList<Integer> tagID(){
		return tagID;
	}
	
	public void setkeyword(String keyword){
		this.keyword = keyword;
	}
	public String keyword(){
		return keyword;
	}
}