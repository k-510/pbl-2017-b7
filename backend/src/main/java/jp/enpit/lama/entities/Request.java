package jp.enpit.lama.entities;

import java.util.Date;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.bson.Document;

@XmlRootElement
public class Request{
	@XmlElement(name="id")
	private int id;
	@XmlElement(name="time")
	private Date time;
	@XmlElement(name="shop")
	private String shop;
	@XmlElement(name="condition")
	private Condition condition = new Condition();
	@XmlElement(name="due")
	private Date due;
	@XmlElement(name="budget")
	private int budget;	
	@XmlElement(name="clentID")
	private int clentID;
	@XmlElement(name="surrogateID")
	private int surrogateID;
	@XmlElement(name="status")
	private String status;
	
	public Request(){
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
		this(id, time);
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

	
	public Request(int id, Date time, String shop, Condition condition, Date due, int budget){
		this(id, time, shop, condition, due);
		setBudget(budget);
	}
	
	public Request(int id, Date time, String shop, Condition condition, Date due, int budget, int clentID){
		this(id, time, shop, condition, due, budget);
		setClentID(clentID);
	}
	
	public Request(int id, Date time, String shop, Condition condition, Date due, int budget, int clentID, int surrogateID){
		this(id, time, shop, condition, due, budget, clentID);
		setSurrogateID(surrogateID);
	}
	
	public Request(int id, Date time, String shop, Condition condition, Date due, int budget, int clentID, int surrogateID, String status){
		this(id, time, shop, condition, due, budget, clentID, surrogateID);
		setStatus(status);
	}

	public Request(int id, Date time, String shop, Document condition, Date due, int budget, int clentID, int surrogateID, String status){
		this(id, time, shop, new Condition(condition), due, budget, clentID, surrogateID, status);
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
	
	public void setCondition(Condition condition){	
		this.condition.setCondition(condition);
	}
	
	public Condition condition(){
		return condition;
	}
	
	public void setDue(Date due){
		this.due = due;
	}
	
	public Date due(){
		return due;
	}
	
	public void setBudget(int budget){
		this.budget = budget;
	}
	
	public int budget(){
		return budget;
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
}