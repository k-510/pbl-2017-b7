package jp.enpit.lama.entities;

import java.util.Collection;
import java.util.Date;

//import javax.json.JsonArray;
import javax.xml.bind.annotation.XmlElement;

import org.bson.Document;

public class Request{
	@XmlElement(name="id")
	private int id;
	@XmlElement(name="time")
	private Date time;
	@XmlElement(name="shop")
	private String shop;
	@XmlElement(name="condition")
	private Condition condition = new Condition() ;
	@XmlElement(name="due")
	private Date due;
	@XmlElement(name="clentID")
	private int clentID;
	@XmlElement(name="surrogateID")
	private int surrogateID;
	@XmlElement(name="status")
	private String status;
	

	public Request(){
		time = new Date();
		due = new Date();
	}

	public Request(int id){
		this();
		setId(id);
	}
	
	public Request(int id, Date time){
		this(id);
		setTime(time);
	}
	
	public Request(int id, Date time, String shop){
		this(id,time);
		setShop(shop);
	}

	public Request(int id, Date time, String shop, Condition condition){
		this(id, time, shop);
		setCondition(condition);
	}

	public Request(int id, Date time, String shop, Condition condition, Date due){
		this(id, time, shop, condition);
		setDue(due);
	}
	
	public Request(int id, Date time, String shop, Condition condition, Date due, int clentID){
		this(id, time, shop, condition, due);
		setClentID(clentID);
	}
	
	public Request(int id, Date time, String shop, Condition condition, Date due, int clentID, int surrogateID){
		this(id, time, shop, condition, due, clentID);
		setSurrogateID(surrogateID);
	}
		
	public Request(int id, Date time, String shop, Condition condition, Date due, int clentID, int surrogateID, String status){
		this(id, time, shop, condition, due, clentID, surrogateID);
		setStatus(status);
	}
		
	public Request(int id, Date time, String shop, Document condition, Date due, int clentID, int surrogateID, String status){
		this(id, time, shop, new Condition(condition), due, clentID, surrogateID, status);
	}
	
	public Request( Date time, String shop, Document condition, Date due, int clentID, int surrogateID, String status){
		setTime(time);
		setShop(shop);
		new Condition(condition);
		setDue(due);
		setClentID(clentID);
		setSurrogateID(surrogateID);
		setStatus(status);
	}
	public Request(int id, int time, String shop, Condition condition, int due, int clentID, int surrogateID, String status){
		setTime(time);
		setShop(shop);
		setCondition(condition);
		setDue(due);
		setClentID(clentID);
		setSurrogateID(surrogateID);
		setStatus(status);
	}
	
	
	///
	/// -----------setter and getter
	///
	public void setId(int id){
		this.id = id;
	}
	public int id(){
		return id;
	}

	public void setTime(Date time){
		this.time = time;
	}
	public void setTime(int time){
		this.time = new Date(time*1000);
	}
	public Date time(){
		return time;
	}
	

	
	public void setShop(String shop){
		if(shop == null)
			throw new NullPointerException();
		this.shop = shop;
	}
	public String shop(){
		return shop;
	}
	
	
	public void setCondition(Condition condition){
		this.condition.setCondition(condition);
	}
	public Document condition(){
		return new Document()
				.append("tagID", condition.tagID())
				.append("keyword",condition.keyword());
	}	

	
	public void setDue(Date due){
		this.due = due;
	}
	public void setDue(int due){
		this.due = new Date(due*1000);
	}
	public Date due(){
		return due;
	}
	
	
	public void setClentID(int clentID){
		this.clentID = clentID;
	}
	public int clentID(){
		return clentID;
	}
	
	
	public void setSurrogateID(int surrogateID){
		this.surrogateID = surrogateID;
	}
	public int surrogateID(){
		return surrogateID;
	}
	
	
	public void setStatus(String status){
		this.status = status;
	}
	public String status(){
		return status;
	}
}	




