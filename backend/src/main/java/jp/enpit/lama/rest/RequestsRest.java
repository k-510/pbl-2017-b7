package jp.enpit.lama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.enpit.lama.entities.ErrorMessage;
import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.User;
import jp.enpit.lama.model.RequestModel;
import jp.enpit.lama.model.UserModel;


@Path("/user/requests")
public class RequestsRest {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response userRequest(@QueryParam("type") String type, @HeaderParam("Accept") String Accept, @HeaderParam("Authorization") String session){
		//session処理
		//if(type == "registered"){
			UserModel usermodel = createUserModel();                                                                                          
			User user = usermodel.findBySession(session);
			if(user == null)
				//return errorMessage(400, "The request you sent contents an invalid parameter.");
				return errorMessage(400, session);
			
			int clentID = user.userID();		
			RequestModel requestmodel = createRequestModel();
			Request request = requestmodel.findByClentId(clentID);
			return Response.status(200)
					.header("Content-Type", "application/json")
					.entity(request)
					.build();
		
		
	}
		
	
	
	
	
	
    private Response findRequestsWithStart(RequestModel model, String startString){
        try{
            int start = Integer.parseInt(startString);
            if(start < 0)
                return errorMessage(400, "Not a positive integer");
            return Response.status(200)
                    .entity(model.findWithStart(start))
                    .build();
        } catch(NumberFormatException e){
            return errorMessage(400, "Bad request");
        }
    }

    private Response findRequestsWithLength(RequestModel model, String lengthString){
        try{
            int length = Integer.parseInt(lengthString);
            if(length < 0)
                return errorMessage(400, "Not a positive integer");
            return Response.status(200)
                    .entity(model.findWithLength(length))
                    .build();
        } catch(NumberFormatException e){
            return errorMessage(400, "Bad request");
        }
    }

	
    private Response findRequestsWithRangeAndFilter(RequestModel model, String startString, String lengthString, String filter){
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
        } catch(NumberFormatException e){
            return errorMessage(400, "Bad request");
        }
    }

    public Response errorMessage(int statusCode, String message){
        return Response.status(statusCode)
        		.header("Content-Type","application/json")
                .entity(new ErrorMessage(message))
                .build();
    }
	
	private Response findRequestList(RequestModel model){
		return Response.status(200)
				.entity(model.findRequests())
				.build();
	}
	
	private int toInteger(String string){
		try{
			return Integer.parseInt(string);
		}catch(NumberFormatException e){
			return -1;
		}
		
	}
	
	private RequestModel createRequestModel(){
		return new RequestModel();
	}
	
	
	private UserModel createUserModel(){
		return new UserModel();
	}

}