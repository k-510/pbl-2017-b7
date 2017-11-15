package jp.enpit.lama.rest;

import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.enpit.lama.entities.Condition;
import jp.enpit.lama.entities.ErrorMessage;
import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.Requests;
import jp.enpit.lama.entities.User;
import jp.enpit.lama.model.RequestModel;
import jp.enpit.lama.model.UserModel;


@Path("/user/requests")
public class UserRequestsRest {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequest(@QueryParam("type") String type, @HeaderParam("Accept") String Accept, @HeaderParam("Authorization") String sessionToken) {

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

		//
		// TODO: リクエストにパラメータが欠けていたときの例外処理が必要です
		//       例: ?type を忘れて GET して来たときなど
		//

		String idtype;
		if(type.equals("registered")) idtype = "clentID";
		else if(type.equals("accepted")) idtype = "surrogateID";
		else
			return errorMessage(400, "[FOR DEBUG] type is wrong");

		int clentID = user.userID();		
		RequestModel model = createRequestModel();
		Requests requests = model.findByUserID(idtype,clentID);		
		return Response.status(200)
				.header("Content-Type", "application/json")
				.entity(requests)
				.build();

	}

	//
	// TODO: 現状のコードでは，datetime や deadline を unixtime (しかも単位は「秒」）で渡す必要がある．
	//       tag_id も [2, 3] ではなく 2,3 と書かなければならない．
	//       また，GET と同様，パラメータが欠けていた際の例外処理をしっかりやろう．
	//
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRequest(@FormParam("datetime") int datetime, @FormParam("shop_id") String shopID, @FormParam("tag_id") String tag, @FormParam("condition") String keyword, @FormParam ("deadline") int deadline, @FormParam ("budget") int budget, @HeaderParam("Authorization") String sessionToken) {

		int id = 0;

		//time はintでないと受け取れなくて

		String[] tagstring = tag.split(",");

		ArrayList<Integer> tagID = new ArrayList<Integer>();
		for(String a : tagstring) {
			tagID.add(Integer.valueOf(a));
		}

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

		int clentID = user.userID();
		String status = "new";//新規のため
		Request request = new Request(-1,datetime,shopID,new Condition(tagID,keyword),deadline,budget,clentID,-1,status);
		RequestModel model = createRequestModel();
		request = model.register(request);
		
		return Response.status(200).entity(request).build();
		
		
	}

		private Response findRequestsWithStart(RequestModel model, String startString) {
				try{
						int start = Integer.parseInt(startString);
						if(start < 0)
								return errorMessage(400, "Not a positive integer");
						return Response.status(200)
										.entity(model.findWithStart(start))
										.build();
				} catch(NumberFormatException e) {
						return errorMessage(400, "Bad request");
				}
		}

		private Response findRequestsWithLength(RequestModel model, String lengthString) {
				try{
						int length = Integer.parseInt(lengthString);
						if(length < 0)
								return errorMessage(400, "Not a positive integer");
						return Response.status(200)
										.entity(model.findWithLength(length))
										.build();
				} catch(NumberFormatException e) {
						return errorMessage(400, "Bad request");
				}
		}

		private Response findRequestsWithRangeAndFilter(RequestModel model, String startString, String lengthString, String filter) {
				try{
						int start = Integer.parseInt(startString);
						int length = Integer.parseInt(lengthString);
						if(start < 0 || length < 0)
								return errorMessage(400, "Not a positive integer");
						if(filter.trim().equals(""))
								return errorMessage(400, "Empty filter");
						return Response.status(200)
										.entity(model.findWithRangeAndFilter(start, length, filter))
										.build();
				} catch(NumberFormatException e) {
						return errorMessage(400, "Bad request");
				}
		}

		public Response errorMessage(int statusCode, String message) {
				return Response.status(statusCode)
						.header("Content-Type","application/json")
								.entity(new ErrorMessage(message))
								.build();
		}

		private Response findRequestsWithFilter(RequestModel model, String filter) {
			if(filter.trim().equals(""))
				return errorMessage(400,"Empty filter");
			return Response.status(200)
					.entity(model.findWithFilter(filter))
					.build();
		}

	private Response findRequestList(RequestModel model) {
		return Response.status(200)
				.entity(model.findRequests())
				.build();
	}

	private int toInteger(String string) {
		try{
			return Integer.parseInt(string);
		}catch(NumberFormatException e) {
			return -1;
		}
		
	}

	private RequestModel createRequestModel() {
		return new RequestModel();
	}

	private UserModel createUserModel() {
		return new UserModel();
	}

}
