package jp.enpit.lama.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import jp.enpit.lama.entities.*;
import jp.enpit.lama.model.*;

@Path("/logout")
public class LogoutRest {

	// DELETE リクエストに対する処理
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@HeaderParam("Authorization") String sessionToken) {
		
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

		// セッションが見つからなければそのまま 204 を返す（RESTful だから）
		if (user == null) {
			return Response.status(204).build();
		}
		
		// セッショントークンを削除して DB に反映
		user.setSession("");
		usermodel.updateUserDocument(user.userID(), user);

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
