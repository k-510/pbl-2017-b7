package jp.enpit.lama.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.enpit.lama.entities.*;
import jp.enpit.lama.model.*;

@Path("/login")
public class LoginRest {

	// POST リクエストに対する処理
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@FormParam("user_id") int user_id, @FormParam("password") String plainPassword) {
		// 平文のパスワードをハッシュ化
		String hashedPassword = "";
		try {
			hashedPassword = getHashedValue(plainPassword);
		} catch (NoSuchAlgorithmException e) {
			return errorMessage(500, e.toString());
		}
		// ユーザID でユーザを検索
		UserModel usermodel = new UserModel();
		User user = usermodel.findByUserID(user_id);
		// パスワードが一致していなければ 403 を返す
		if (user == null || !hashedPassword.equals(user.hashedPassword())) {
			return errorMessage(403, "Incorrect username or password.");
		}
		// セッショントークン（ランダムな文字列）を生成
		String sessionToken = UUID.randomUUID().toString();
		// 新しいセッショントークンを DB に反映
		user.setsession(sessionToken);
		usermodel.updateUserInDB(user.userID(), user);
		// レスポンスデータの準備
		SessionToken response = new SessionToken(sessionToken);
		return Response.status(201)
		               .entity(response)
		               .build();
	}
	
	// 平文のパスワードをハッシュ化する (md5)
	// source code: http://rmrmrmarmrmrm.seesaa.net/article/409693028.html
	private String getHashedValue(String plain_password) throws NoSuchAlgorithmException {
		String hashedPassword = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain_password.getBytes());
			byte[] hashBytes = md.digest();
			int[] hashInts = new int[hashBytes.length];
			StringBuilder sb = new StringBuilder();
			for (int i=0; i < hashBytes.length; i++) {
				hashInts[i] = (int)hashBytes[i] & 0xff;
				if (hashInts[i] <= 15) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(hashInts[i]));
			}
			hashedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
		return hashedPassword;
	}
	
	// エラーメッセージを含むレスポンスデータの生成
	private Response errorMessage(int statusCode, String message){
		return Response.status(statusCode)
		               .header("Content-Type", "application/json")
		               .entity(new ErrorMessage(message))
		               .build();
	}

}
