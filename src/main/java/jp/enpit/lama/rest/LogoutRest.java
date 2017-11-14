package jp.enpit.lama.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/logout")
public class LogoutRest {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login() {
		return Response.status(200)
                .entity("Hello, world!")
                .build();
	}
}
