/**
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.portizsan.twitrector.tasks;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

import es.portizsan.twitrector.bean.Twitrector;
import es.portizsan.twitrector.service.TwitrectorService;

public class TweetSearchTask extends HttpServlet {
	private static final long serialVersionUID = -1243223937144208948L;
	protected static final Logger logger = Logger
			.getLogger(TweetSearchTask.class.getName());

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		logger.info("TweetSearchServlet started");
		try {
			List<Twitrector> trl = new TwitrectorService().getTwitrectors();
			if (trl == null || trl.isEmpty()) {
				logger.log(Level.WARNING, "No Twitrectors found!!!!!");
				return;
			}
			for (Twitrector tr : trl) {
				logger.info("Searching for :" + tr.getQuery());
				String search = tr.getQuery();
				Twitter twitter = new TwitterFactory().getInstance();
				Query query = new Query(search);
				long before = System.currentTimeMillis() - (1000 * 60 * 60);
				logger.info("from :" + before);
				query.setSinceId(before);
				query.setLocale("es");
				query.setCount(100);
				if (tr.getLocation() != null) {
					GeoLocation location = new GeoLocation(tr.getLocation()
							.getLatitude(), tr.getLocation().getLongitude());
					Unit unit = Unit.valueOf(tr.getLocation().getUnit().name());
					query.setGeoCode(location, tr.getLocation().getRadius(),
							unit);
				}
				QueryResult result;
				do {
					result = twitter.search(query);
					List<Status> tweets = result.getTweets();
					for (Status tweet : tweets) {
						Queue queue = QueueFactory.getQueue("default");
						queue.add(TaskOptions.Builder
								.withUrl("/tasks/tweetReply")
								.param("statusId",
										String.valueOf(tweet.getId()))
								.param("message",
										"@"
												+ tweet.getUser()
														.getScreenName()
												+ " "
												+ String.valueOf(tr
														.getResponse())));

						logger.info("@" + tweet.getUser().getScreenName()
								+ " - " + tweet.getText());
					}
				} while ((query = result.nextQuery()) != null);
			}
		} catch (TwitterException te) {
			logger.log(Level.WARNING, "Failed to search tweets: ", te);
		}
	}
}
