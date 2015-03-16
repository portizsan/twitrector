package es.portizsan.twitrector.service;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;

import es.portizsan.twitrector.bean.OAuthCredentials;

public class OAuthCredentialsService {
	public OAuthCredentials getActiveUser() {
		return ofy().load().type(OAuthCredentials.class).filter("active", true)
				.first().now();
	}

	public OAuthCredentials saveOAuthCredentials(
			OAuthCredentials oAuthCredentials) {
		Key<OAuthCredentials> id = ofy().save().entity(oAuthCredentials).now();
		return oAuthCredentials;
	}
}
