package jp.enpit.lama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user/requests")
public class RequestsRest {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response userRequest(@QueryParam("type") String name){
		String msg = "a";
		
		return Response.status(200).entity(msg).build();
	}

}