package es.portizsan.twitrector.ws;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import es.portizsan.twitrector.bean.Twitrector;
import es.portizsan.twitrector.service.TwitrectorService;

@Path("/twitrectors")
public class TwitrectorWS {

	protected static final Logger logger = Logger.getLogger(TwitrectorWS.class
			.getName());

	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getTwitrectors(@Context HttpServletRequest request) {
		return Response
				.status(Status.OK)
				.entity(new Gson().toJson(new TwitrectorService()
						.getTwitrectors())).build();
	}

	@GET
	@Path("/twitrector/{id}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getTwitrector(@Context HttpServletRequest request,
			@PathParam(value = "id") String id) {
		return Response
				.status(Status.OK)
				.entity(new Gson().toJson(new TwitrectorService()
						.getTwitrector(id))).build();
	}

	@POST
	@Path("/twitrector")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response crreateTwitrectors(@Context HttpServletRequest request,
			@FormParam(value = "json") String json) {
		if (json.equals(null))
			return Response.status(Status.BAD_REQUEST).build();
		logger.info(json);
		Twitrector twitrector = new Gson().fromJson(json, Twitrector.class);
		logger.info(twitrector.toString());
		return Response
				.status(Status.OK)
				.entity(new Gson().toJson(new TwitrectorService()
						.saveTwitrector(twitrector))).build();
	}
}
