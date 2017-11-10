package jp.enpit.lama.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

public class Request {
	@XmlElement(name="id")
	private int id;
	@XmlElement(name="time")
	private Date time;
	@XmlElement(name="shop")
	private String shop;
	@XmlElement(name="condition")
	private String condition;
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
	
	public Request(String shop){
		this();
		setShop(shop);
	}

	public Request(int id, String shop){
		this(shop);
		setId(id);
	}

	public Request(int id, String shop, Date time){
		this(id, shop);
		this.time = time;
	}
	
	public Request(int id, String shop, String condition){
		this(id, shop);
		setCondition(condition);
	}
	
	public Request(int id, String shop, String condition, Date due){
		this(id, shop, condition);
		this.due = due;
	}
	
	public Request(int id, String shop, String condition, int clentID){
		this(id, shop, condition);
		setClentID(clentID);
	}
	
	public Request(int id, String shop, String condition, int clentID, int surrogateID){
		this(id, shop, condition, clentID);
		setSurrogateID(surrogateID);
	}
	
	public Request(int id, String shop, String condition, int clentID, int surrogateID, String status){
		this(id, shop, condition, clentID, surrogateID);
		setStatus(status);
	}
	public void setTime(Date time){
		this.time = time;
	}
	
	public Date time(){
		return time;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int id(){
		return id;
	}
	
	public void setShop(String shop){
		if(shop == null)
			throw new NullPointerException();
		this.shop = shop;
	}
	
	public String shop(){
		return shop;
	}
	
	public void setCondition(String condition){
		this.condition = condition;
	}
	
	public String condition(){
		return condition;
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




