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

import jp.enpit.lama.entities.User;
import jp.enpit.lama.entities.Users;

public class UserModel extends BaseModel{
	private MongoCollection<Document> requests(){
		return super.collection("users");
	}
	
    public User findBySession(String session){
        Document document = requests().find(eq("session", session))
                .limit(1).first();
        return toUser(document);
    }
	
	public User findByUserID(int id){
		Document document = requests().find(eq("userID", id))
		                              .limit(1).first();
		return toUser(document);
	}
	
    //使用しないはず
    public void deleteUser(String id){
        requests().deleteOne(eq("session", id));
    }
    //使用しないはず
    private MongoCollection<Document> latestClentIds(){
        return super.collection("latestClentIDs");
    }

    //
    //これは使うにはなにかしらしないといけない。
    //
    public User register(User user ){
        user.setUserID(latestId() + 1);
        requests().insertOne(toDocument(user));
        latestClentIds().insertOne(new Document("clentID", user.userID()));
        return user;
    }
    //使用しないはず
    public int latestId(){
        MongoCollection<Document> cids = latestClentIds();
        if(cids.count() == 0L)
            return 0;
        return cids.find()
                .sort(descending("cid"))
                .first()
                .getInteger("cid", 0);
    }

    public Users findRequests(){
        return new Users(toList(list()));
    }

    public Users findWithRange(int start, int length){
        return new Users(toList(list()
                .skip(start)
                .limit(length)));
    }

    public Users findWithLength(int length){
        return new Users(toList(list()
                .limit(length)));
    }

    public Users findWithStart(int start){
        return new Users(toList(list()
                .skip(start)));
    }

    public Users findWithFilter(String filter){
        return new Users(toList(list(filter)));
    }

    public Users findWithStartAndFilter(int start, String filter){
        return new Users(toList(list(filter)
                .skip(start)));
    }

    public Users findWithLengthAndFilter(int length, String filter){
        return new Users(toList(list(filter)
                .limit(length)));
    }
	
    public Users findWithRangeAndFilter(int start, int length, String filter){
        return new Users(toList(list(filter)
                .skip(start)
                .limit(length)));
    }
	
    private List<User> toList(FindIterable<Document> iterable){
        List<User> list = new ArrayList<>();
        for(Document document: iterable){
            list.add(toUser(document));
        }
        return list;
    }

    private FindIterable<Document> list(){
        return requests().find()
                .sort(ascending("clentID"));
    }
	
    private FindIterable<Document> list(String filter){
        return requests().find()
                .filter(regex("clentID", Pattern.compile(filter)))
                .sort(descending("id"));
    }
	
	private Document toDocument(User user){
		return new Document().append("userName", user.userName())
		                     .append("userID", user.userID())
		                     .append("pswd", user.hashedPassword())
		                     .append("email", user.email())
		                     .append("session", user.session());
	}
	
	
	private User toUser(Document document){
		if(document == null)
			return null;
		return new User(
				document.getString("userName"),
				document.getInteger("userID",0),
				document.getString("pswd"),
				document.getString("email"),
				document.getString("session")
		);
	}
	
	/**
	 * user オブジェクトの内容で DB の該当ドキュメント（エントリ）を更新（上書き）する
	 * @param userID 更新したいドキュメントの現在のユーザ ID
	 * @param user 更新したい内容
	 */
	public void updateUserDocument(int userID, User user) {
		requests().findOneAndReplace(eq("userID", userID), toDocument(user));
	}
	
}
