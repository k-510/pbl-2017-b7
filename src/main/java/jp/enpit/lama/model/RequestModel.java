package jp.enpit.lama.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.Requests;

public class RequestModel{
    private MongoCollection<Document> Requests(){
        return MongoClientPool.getInstance()
                .database().getCollection("requests");
    }

    public Request findById(int id){
        Document document = Requests().find(eq("id", id))
                .limit(1).first();
        return toRequest(document);
    }

    public Requests find(){
        Iterable<Document> iterable = Requests().find()
                .sort(ascending("id"));
        return convertToRequests(iterable);
    }

    private Requests convertToRequests(Iterable<Document> iterable){
        List<Request> list = new ArrayList<>();
        for(Document document: iterable){
            list.add(toRequest(document));
        }
        return new Requests(list);
    }
    
    private Document toDocument(Request request) {
    	Document condition = new Document();
    	condition.append("tagID", request.condition().tagID())
    	         .append("keyword", request.condition().keyword());
		return new Document().append("id", request.id())
                             .append("time", request.time())
                             .append("shop", request.shop())
                             .append("condition", condition)
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
		Requests().findOneAndReplace(eq("id", requestID), toDocument(request));
	}

}