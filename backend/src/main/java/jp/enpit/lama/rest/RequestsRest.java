package jp.enpit.lama.rest;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.stream.JsonParser;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bson.Document;
import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import org.glassfish.grizzly.InputSource;

import jp.enpit.lama.entities.Condition;
import jp.enpit.lama.entities.ErrorMessage;
import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.User;
import jp.enpit.lama.model.RequestModel;
import jp.enpit.lama.model.UserModel;


@Path("/user/requests")
public class RequestsRest {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequest(@QueryParam("type") String type, @HeaderParam("Accept") String Accept, @HeaderParam("Authorization") String session){
		UserModel usermodel = createUserModel();                                                                                          
		User user = usermodel.findBySession(session);
		if(user == null)
			return errorMessage(400, "[FOR DEBUG], canot find this user ");
		
		if(type.equals("registered")){
			int clentID = user.userID();		
			RequestModel requestmodel = createRequestModel();
			Request request = requestmodel.findByUserId("clentID", clentID);
			return Response.status(200)
					.header("Content-Type", "application/json")
					.entity(request)
					.build();
		}
		else if(type.equals("accepted")){
			int surrogateID = user.userID();
			RequestModel requestmodel = createRequestModel();
			Request request = requestmodel.findByUserId("surrogateID", surrogateID);
			return Response.status(200)
					.header("Content-type", "application/json")
					.entity(request)
					.build();
		}
		
		return errorMessage(400, "[FOR DEBUG] the type is wrong:"+type);		
		
	}
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRequest(@FormParam("time") int time, @FormParam("shop") String shop, @FormParam("tagID") String tag, @FormParam("keyword") String keyword, @FormParam ("due") int due, @FormParam("surrogateID") int surrogateID, @HeaderParam("Authorization") String session){
		
		int id = 0;
		//time はintでないと受け取れなくて
		String[] tagstring = tag.split(",");
		
		ArrayList<Integer> tagID = new ArrayList<Integer>();
		for(String a : tagstring){
			tagID.add(Integer.valueOf(a));
		}
		
		UserModel usermodel = createUserModel();                                                                                          
		User user = usermodel.findBySession(session);
		if(user == null)
			return errorMessage(400, "[FOR DEBUG], canot find this user ");//たぶんでない
		int clentID = user.userID();
		
		String status = "new";//新規のため
		try(RequestModel model = createRequestModel()){
			Request request = model.register(id,time,shop,tagID,keyword,due,clentID,surrogateID,status);
			return Response.status(200)
					.entity(request)
					.build();
		}
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