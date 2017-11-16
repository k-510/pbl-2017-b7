package jp.enpit.lama.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import org.bson.Document;

@XmlRootElement
public class Request {

	@XmlElement(name="request_id")
	private int id;
	@XmlElement(name="datetime")
	private Date time;
	@XmlElement(name="shop_id")
	private String shop;
	@XmlElement(name="condition")
	private Condition condition = new Condition();
	@XmlElement(name="deadline")
	private Date due;
	@XmlElement(name="budget")
	private int budget;	
	@XmlElement(name="clentID")
	private int clentID;
	@XmlElement(name="surrogateID")
	private int surrogateID;
	@XmlElement(name="status")
	private String status;

	public Request() {
		time = new Date();
		due  = new Date();
	}

	public Request(int id, Date time, String shop, Condition condition, Date due, int budget, int clentID, int surrogateID, String status) {
		setId(id);
		setTime(time);
		setShop(shop);
		setCondition(condition);
		setDue(due);
		setBudget(budget);
		setClentID(clentID);
		setSurrogateID(surrogateID);
		setStatus(status);
	 }

	public Request(int id, Date time, String shop, Document condition, Date due, int budget, int clentID, int surrogateID, String status) {
		this(id, time, shop, new Condition(condition), due, budget, clentID, surrogateID, status);
	}
	
	public Request(int id, int time, String shop, Condition condition, int due, int budget, int clentID, int surrogateID, String status) {
		this(id, new Date(time*1000), shop, condition, new Date(due*1000), budget, clentID, surrogateID, status);
	}
	
	public Request(int id, int time, String shop, Document condition, int due, int budget, int clentID, int surrogateID, String status) {
		this(id, new Date(time*1000), shop, new Condition(condition), new Date(due*1000), budget, clentID, surrogateID, status);
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public int id() {
		return id;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setTime(int time) {
		this.time = new Date(time*1000);
	}

	public Date time() {
		return time;
	}

	public void setShop(String shop) {
		if(shop == null)
			throw new NullPointerException();
		this.shop = shop;
	}

	public String shop() {
		return shop;
	}

	public void setCondition(Condition condition) {	
		this.condition.setCondition(condition);
	}

	public Document condition() {
		return new Document()
				.append("tagID", condition.tagID())
				.append("keyword",condition.keyword());
	}

	public void setDue(Date due) {
		this.due = due;
	}

	public void setDue(int due) {
		this.due = new Date(due*1000);
	}

	public Date due() {
		return due;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int budget() {
		return budget;
	}

	public void setClentID(int clentID) {
		this.clentID = clentID;
	}

	public int clentID() {
		return clentID;
	}

	public void setSurrogateID(int surrogateID) {
		this.surrogateID = surrogateID;
	}

	public int surrogateID() {
		return surrogateID;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String status() {
		return status;
	}

}
