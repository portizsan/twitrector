package es.portizsan.twitrector.service;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import es.portizsan.twitrector.bean.OAuthCredentials;

public class TwitterService {

	private Twitter twitter;

	public Twitter getTwitterInstance() {
		if (twitter == null) {
			OAuthCredentials user = new OAuthCredentialsService()
					.getActiveUser();
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(false)
					.setOAuthConsumerKey(user.getConsumerKey())
					.setOAuthConsumerSecret(user.getConsumerSecret())
					.setOAuthAccessToken(user.getAccessToken())
					.setOAuthAccessTokenSecret(user.getAccessTokenSecret());
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();
		}
		return twitter;
	}

}
