package jp.enpit.lama.entities;

import javax.xml.bind.annotation.XmlElement;

public class Request {

	@XmlElement(name = "shop_id")
	private String shop_id;
	@XmlElement(name = "dateime")
	private int datetime;
	@XmlElement(name = "condition")
	private String condition;
	@XmlElement(name = "deadline")
	private int deadline;
	@XmlElement(name = "request_id")
	private int request_id;
	@XmlElement(name = "client_id")
	private int client_id;
	@XmlElement(name = "surrogate_id")
	private int surrogate_id;
	
	
	public Request(){
	
	}
	
	public Request(String shopid, int datetime, String condition, int deadline, int requestid, int clientid, int surrogateid){
		this.setShopId(shopid);
		this.setDeadline(deadline);
		this.setCondition(condition);
		this.setDeadline(deadline);
		this.setRequestId(requestid);
		this.setClientId(clientid);
		this.setSurrogateId(surrogateid);
	}
	public String shopid(){
		return shop_id;
	}
	public void setShopId(String shopID){
		this.shop_id = shopID;
	}
	public int datetime(){
		return datetime;
	}
	public void setDatetime(int datetime){
		this.datetime = datetime;
	}
	public String condition(){
		return condition;
	}
	public void setCondition(String condition){
		this.condition = condition;
	}
	public int deadLine(){
		return deadline;
	}
	public void setDeadline(int deadline){
		this.deadline = deadline;
	}
	public int requestId(){
		return request_id;
	}
	public void setRequestId(int requestID){
		this.request_id = requestID;
	}
	public int clientId(){
		return client_id;
	}
	public void setClientId(int clientID){
		this.client_id = clientID;
	}
	public int surrogateId(){
		return surrogate_id;
	}
	public void setSurrogateId(int surrogateID){
		this.surrogate_id = surrogateID;
	}

}	


/*

    "shop_id": "k682891",
    "datetime": "2017-10-27T15:00:00.000Z",
    "condition": "テーブルマナーがある人",
    "deadline": "2017-11-01T12:00:00.000Z",
    "request_id": 1,
    "client_id": 1,
    "surrogate_id": 2

*/