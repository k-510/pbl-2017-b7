package jp.enpit.lama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.enpit.lama.entities.Chat;
import jp.enpit.lama.entities.Chats;
import jp.enpit.lama.entities.ErrorMessage;
import jp.enpit.lama.model.ChatModel;
import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.User;
import jp.enpit.lama.model.RequestModel;
import jp.enpit.lama.model.UserModel;

@Path("/user/requests/{id}/chats")
public class ChatsRest {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChat(@PathParam("id") String idString, @HeaderParam("Authorization") String sessionToken) {

		// Authorization ヘッダは Authorization: Session {token} の形式
		// {token} の部分だけ切り出す
		if (sessionToken == null) {
			return errorMessage(403, "Your request contains no session tokens.");
		}
		int index = sessionToken.indexOf("Session ");
		if (index != 0) {
			return errorMessage(403, "Your request contains no session tokens.");
		}
		sessionToken = sessionToken.substring(8);

		// セッショントークンからユーザを検索する
		UserModel usermodel = new UserModel();
		User user = usermodel.findBySession(sessionToken);

		// セッションが見つからなければ 403 を返す
		if (user == null) {
			return errorMessage(403, "No such session token.");
		}
		
		ChatModel model = new ChatModel();
		RequestModel reqModel = new RequestModel();			
		
		int id = toInteger(idString);

		Request request = reqModel.findById(id);
		
		if(id <= 0)
			return errorMessage(400, "Bad Request");
		
		// [404] 依頼が存在するか判定
		if(request == null)
			return errorMessage(404, "The resource you were looking for could not be found");

		// [403] ログインユーザ判定
		if(request.clentID() != user.userID() & request.surrogateID() != user.userID())
			return errorMessage(403, "The request whose chat log you want to get is neigther registered nor accepted by you.");
		
		
		Chats chats = model.findById(id);
		return Response.status(200)
				.entity(chats)
				.build();
	}

	public Response errorMessage(int statusCode, String message) {
		return Response.status(statusCode)
				.entity(new ErrorMessage(message))
				.build();
	}

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response findComments() {
//        ChatModel model = new ChatModel();
//        return Response.status(200)
//                .entity(model.find())
//                .build();
//    }
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postChat(@FormParam("message") String message, @PathParam("id") String idString, @HeaderParam("Authorization") String sessionToken) {

		// Authorization ヘッダは Authorization: Session {token} の形式
		// {token} の部分だけ切り出す
		if (sessionToken == null) {
			return errorMessage(403, "Your request contains no session tokens.");
		}
		int index = sessionToken.indexOf("Session ");
		if (index != 0) {
			return errorMessage(403, "Your request contains no session tokens.");
		}
		sessionToken = sessionToken.substring(8);

		// セッショントークンからユーザを検索する
		UserModel usermodel = new UserModel();
		User user = usermodel.findBySession(sessionToken);

		// セッションが見つからなければ 403 を返す
		if (user == null) {
			return errorMessage(403, "No such session token.");
		}

		if(message == null || message.trim().equals(""))
			return errorMessage(400, "Empty Message");
		if(message.length() > 200)
			return errorMessage(400, "Too long message");

		RequestModel reqModel = new RequestModel();

		int id = toInteger(idString);

		Request request = reqModel.findById(id);

		if(id <= 0)
			return errorMessage(400, "Bad Request");

		// [404] 依頼が存在するか判定
		if(request == null)
			return errorMessage(404, "The resource you were looking for could not be found.");

		// [403] ログインユーザ判定
		if(request.clentID() != user.userID() & request.surrogateID() != user.userID())
			return errorMessage(403, "The request whose chat log you want to get is neigther registered nor accepted by you.");

		ChatModel model = new ChatModel();
		Chat chat = model.register(new Chat(id, user.userID(), message));

		return Response.status(201).entity(message).build();
	}

	private int toInteger(String string) {
		try{
			return Integer.parseInt(string);
		} catch(NumberFormatException e) {
			return -1;
		}
	}

}
