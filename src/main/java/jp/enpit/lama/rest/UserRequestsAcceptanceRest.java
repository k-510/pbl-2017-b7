package jp.enpit.lama.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.enpit.lama.entities.*;
import jp.enpit.lama.model.*;

@Path("/user/requests/{id}/acceptance")
public class UserRequestsAcceptanceRest {

	// PUT リクエストに対する処理
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response completion(@HeaderParam("Authorization") String sessionToken, @PathParam("id") String idString) {

		// Authorization ヘッダは Authorization: Session {token} の形式
		// {token} の部分だけ切り出す
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

		// 指定された依頼を検索する
		int id = 0;
		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			return errorMessage(404, "The resource you were looking for could not be found.");
		}
		RequestModel requestmodel = new RequestModel();
		Request request = requestmodel.findById(id);
		if (request == null) {
			return errorMessage(404, "The resource you were looking for could not be found.");
		}

		// 依頼に代行者が割り当てられていないか確認
		if (!request.status().equals("new")) {
			return errorMessage(409, "The request you want to accept has another surrogate already.");
		}

		// 代行者を割り当てて DB を更新
		request.setSurrogateID(user.userID());
		request.setStatus("accepted");
		requestmodel.updateRequestDocument(request.id(), request);

		return Response.status(204).build();

	}

	// エラーメッセージを含むレスポンスデータの生成
	private Response errorMessage(int statusCode, String message){
		return Response.status(statusCode)
		               .header("Content-Type", "application/json")
		               .entity(new ErrorMessage(message))
		               .build();
	}

}
