package es.portizsan.twitrector.ws;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import es.portizsan.twitrector.bean.OAuthCredentials;
import es.portizsan.twitrector.service.OAuthCredentialsService;

@Path("/oAuthCredentials")
public class OAuthCredentialsWS {

	protected static final Logger logger = Logger
			.getLogger(OAuthCredentialsWS.class.getName());

	@GET
	@Path("/oAuthCredential/active")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response getOAuthCredential(@Context HttpServletRequest request) {
		return Response
				.status(Status.OK)
				.entity(new Gson().toJson(new OAuthCredentialsService()
						.getActiveUser())).build();
	}

	@POST
	@Path("/oAuthCredential")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response createOAuthCredential(@Context HttpServletRequest request,
			@FormParam(value = "json") String json) {
		if (json.equals(null))
			return Response.status(Status.BAD_REQUEST).build();
		logger.info(json);
		OAuthCredentials oAuthCredentials = new Gson().fromJson(json,
				OAuthCredentials.class);
		logger.info(oAuthCredentials.toString());
		return Response
				.status(Status.OK)
				.entity(new Gson().toJson(new OAuthCredentialsService()
						.saveOAuthCredentials(oAuthCredentials))).build();
	}
}
