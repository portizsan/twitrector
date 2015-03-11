package es.portizsan.twitrector.service;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;

import es.portizsan.twitrector.bean.Twitrector;

public class TwitrectorService {

	public Twitrector getFirstTwitrector() {
		return ofy().load().type(Twitrector.class).first().now();
	}

	public Twitrector getTwitrector(String idTwitrector) {
		return ofy().load().type(Twitrector.class).filter("id", idTwitrector)
				.first().now();
	}

	public List<Twitrector> getTwitrectors() {
		return ofy().load().type(Twitrector.class).list();
	}

	public Twitrector saveTwitrector(Twitrector twitrector) {
		Key<Twitrector> id = ofy().save().entity(twitrector).now();
		return twitrector;
	}

}
