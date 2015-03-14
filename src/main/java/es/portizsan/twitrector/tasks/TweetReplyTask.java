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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetReplyTask extends HttpServlet {
	private static final long serialVersionUID = -1243223937144208948L;
	protected static final Logger logger = Logger
			.getLogger(TweetReplyTask.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String message = null;
		if (req.getParameter("message") != null) {
			message = req.getParameter("message");
		} else {
			logger.log(Level.WARNING, "message is null.");
			return;
		}
		long inReplyToStatusId = 0;
		if (req.getParameter("statusId") != null) {
			try {
				inReplyToStatusId = Long
						.parseLong(req.getParameter("statusId"));
			} catch (NumberFormatException nfe) {
				logger.log(Level.WARNING, "Invalid statusId.", nfe);
				return;
			}
		} else {
			logger.log(Level.WARNING, "statusId is null.");
			return;
		}
		try {
			logger.info("repling: " + message + " , " + inReplyToStatusId);
			Twitter twitter = new TwitterFactory().getInstance();
			StatusUpdate su = new StatusUpdate(message);
			su.setInReplyToStatusId(inReplyToStatusId);
			twitter.updateStatus(su);
		} catch (TwitterException te) {
			logger.log(Level.WARNING, "Failed to search tweets: ", te);
		}
	}
}
