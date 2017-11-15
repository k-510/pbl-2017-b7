package jp.enpit.lama.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.Requests;

public class RequestModel extends BaseModel{
    private MongoCollection<Document> requests(){
        return MongoClientPool.getInstance()
                .database().getCollection("requests");
    }

    public Request findById(int id){
        Document document = requests().find(eq("id", id))
                .limit(1).first();
        return toRequest(document);
    }
    
    public Requests findByUserID(String type,int clentID){
		Document query = new Document(type, clentID);
		FindIterable<Document>iterator = requests().find(query);
	 
		return new Requests(toList(iterator));
	}

    public Requests find(){
        Iterable<Document> iterable = requests().find()
                .sort(ascending("id"));
        return convertToRequests(iterable);
    }
   
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
    
    private Document toDocument(Request request) {
		return new Document().append("id", request.id())
                             .append("time", request.time())
                             .append("shop", request.shop())
                             .append("condition", request.condition())
                             .append("due", request.due())
                             .append("budget", request.budget())
                             .append("clentID", request.clentID())
                             .append("surrogateID", request.surrogateID())
                             .append("status", request.status());
    }
    
    private Request toRequest(Document document){
        if(document == null)
            return null;
        
        return new Request(
        		document.getInteger("id", -1),
        		document.getDate("time"),
                document.getString("shop"),
                (Document)document.get("condition"),
                document.getDate("due"),
                document.getInteger("budget", -1),
                document.getInteger("clentID", -1),
                document.getInteger("surrogateID", -1),
                document.getString("status")
                );
    }
    
    /**
	 * request オブジェクトの内容で DB の該当ドキュメント（エントリ）を更新（上書き）する
	 * @param requestID 更新したいドキュメントの現在のリクエスト ID
	 * @param request 更新したい内容
	 */
	public void updateRequestDocument(int requestID, Request request) {
		requests().findOneAndReplace(eq("id", requestID), toDocument(request));
	}

}
