package jp.enpit.lama.model;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.glassfish.grizzly.http.server.Request;

import com.mongodb.client.MongoCollection;

import jp.enpit.lama.entities.Chat;
import jp.enpit.lama.entities.Chats;

public class ChatModel{
	private MongoCollection<Document> chats(){
		return MongoClientPool.getInstance()
				.database().getCollection("chats");
	}
	
	public Chat findOne(int id){
		Document document = chats().find(eq("requestID", id)).limit(1).first();
		return toChat(document);
	}
	
	public Chats findById(int id){
		Iterable<Document> iterable = chats().find(eq("requestID", id));
		return convertToChats(iterable);
	}
	
	public Chats find(){
		Iterable<Document> iterable = chats().find()
				.sort(ascending("_id"));
		return convertToChats(iterable);
	}

	private MongoCollection<Document> latestIds(){
		return MongoClientPool.getInstance()
				.database().getCollection("latestids");
	}
	
	public Chat register(Chat chat){
		chats().insertOne(toDocument(chat));
		return chat;
	}
	
	private Chats convertToChats(Iterable<Document> iterable){
		List<Chat> list = new ArrayList<>();
		for(Document document: iterable){
			list.add(toChat(document));
		}
		return new Chats(list);
	}
	
	private Document toDocument(Chat chat){
		return new Document()
				.append("requestID", chat.requestID())
				.append("userID", chat.userID())
				.append("message", chat.message());
	}
	
	private Chat toChat(Document document){
		if(document == null)
			return null;
		return new Chat(
				document.getInteger("requestID", 0),
				document.getInteger("userID", 0),
				document.getString("message")
				);
	}
}