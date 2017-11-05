package jp.enpit.lama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.enpit.lama.entities.Request;
import jp.enpit.lama.entities.ErrorMessage;
import jp.enpit.lama.model.RequestModel;

@Path("/requests")
public class RequestsRest {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{cid}")
    public Response getComment(@PathParam("cid") String idString){
        RequestModel model = new RequestModel();
        int cid = toInteger(idString);
        if(cid <= 0)
            return errorMessage(400, "Bad request");

        Request request = model.findById(cid);
        if(request == null)
            return errorMessage(404, "Not found");

        return Response.status(200)
                .entity(request)
                .build();
    }

    public Response errorMessage(int statusCode, String message){
        return Response.status(statusCode)
                .entity(new ErrorMessage(message))
                .build();
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findComments() {
        RequestModel model = new RequestModel();
        return Response.status(200)
                .entity(model.find())
                .build();
    }


    private int toInteger(String string){
        try{
            return Integer.parseInt(string);
        } catch(NumberFormatException e){
            return -1;
        }
    }
}