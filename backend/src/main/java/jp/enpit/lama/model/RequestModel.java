package jp.enpit.lama.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bson.Document;
import org.xml.sax.SAXException;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.Requests;
import jp.enpit.lama.entities.Condition;

public class RequestModel extends BaseModel{
	private MongoCollection<Document> requests(){
		return super.collection("requests");
	}
	
	public Requests findByUserID(String type,int clentID){
		Document query = new Document(type, clentID);
		FindIterable<Document>iterator = requests().find(query);
	 
		return new Requests(toList(iterator));
	}

	/*
    public Document findByClentId(int clentID){
       	Document re = (Document) requests().find(eq("clentID",clentID));
        Document document = requests().find(eq("clentID", clentID))
                .limit(100).first();
        return re;
    }
	
    
    public Request findByUserId(String type, int ID){
    	if(type == "clentID"){
    		Document document = requests().find(eq("clentID", ID))
    				.limit(1).first();
    		return toRequest(document);
    	}
    	else if(type == "surrogateID"){
    		Document document = requests().find(eq("surrogateID", ID))
    				.limit(1).first();
    		return toRequest(document);
    	}
    	else return null;
    }
	*/
    public void deleteRequest(int id){
        requests().deleteOne(eq("id", id));
    }

    
    private MongoCollection<Document> latestClentIds(){
        return super.collection("requests");
    }
    
    
    //
    //
    //
    public Request register(Request request){
        request.setId(latestId() + 1);
        requests().insertOne(toDocument(request));
        //latestClentIds().insertOne(new Document("id", request.id()));
        return request;
    }
     
    public int latestId(){
        MongoCollection<Document> cids = latestClentIds();
        if(cids.count() == 0L)
            return 0;
        return cids.find()
                .sort(descending("id"))
                .first()
                .getInteger("id", 0);
    }

    public Requests findRequests(){
        return new Requests(toList(list()));
    }

    public Requests findWithRange(int start, int length){
        return new Requests(toList(list()
                .skip(start)
                .limit(length)));
    }

    public Requests findWithLength(int length){
        return new Requests(toList(list()
                .limit(length)));
    }

    public Requests findWithStart(int start){
        return new Requests(toList(list()
                .skip(start)));
    }

    public Requests findWithFilter(String filter){
        return new Requests(toList(list(filter)));
    }

    public Requests findWithStartAndFilter(int start, String filter){
        return new Requests(toList(list(filter)
                .skip(start)));
    }

    public Requests findWithLengthAndFilter(int length, String filter){
        return new Requests(toList(list(filter)
                .limit(length)));
    }
	
    public Requests findWithRangeAndFilter(int start, int length, String filter){
        return new Requests(toList(list(filter)
                .skip(start)
                .limit(length)));
    }
	
    private Requests convertToRequests(Iterable<Document> iterable){
        List<Request> list = new ArrayList<>();
        for(Document document: iterable){
            list.add(toRequest(document));
        }
        return new Requests(list);
    }
    
    private List<Request> toList(FindIterable<Document> iterable){
        List<Request> list = new ArrayList<>();
        for(Document document: iterable){
            list.add(toRequest(document));
        }
        return list;
    }

    private FindIterable<Document> list(){
        return requests().find()
                .sort(ascending("id"));
    }
	
    private FindIterable<Document> list(String filter){
        return requests().find()
                .filter(regex("clentID", Pattern.compile(filter)))
                .sort(descending("id"));
    }
	
	private Document toDocument(Request request){
		return new Document()
				.append("id", request.id())
				.append("time", request.time())
				.append("shop", request.shop())
				.append("condition", new Document(request.condition()))
				.append("due", request.due())
				.append("clentID", request.clentID())
				.append("surrogateID", request.surrogateID())
				.append("status", request.status());
	}
	
	
	private Request toRequest(Document document){
		if(document == null)
			return null;
		
		return new Request(document.getInteger("id",0),
				document.getDate("time"),
				document.getString("shop"),
				(Document)document.get("condition"),
				document.getDate("due"),
				document.getInteger("clentID"),
				document.getInteger("surrogateID"),
				document.getString("status"));
	}
	
}

	

