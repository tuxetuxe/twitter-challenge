package com.luis.twitter.api;

import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.TweetList;

/**
 * A RESTfull controller that handles all the operations related with tweets
 * 
 * @author luis
 *
 */
public interface TweetController {

	/**
	 * Return the list of tweets that compose the user timeline (his, and the ones from the users that he follows).
	 * An optional search string can be used to filter out tweets.
	 * 
	 * @param username The user for which the timeline will be fetched
	 * @param searchString An optional search parameter to filter out tweets by its contents
	 * @return A list of tweets matching the timeline rules
	 */
	TweetList getTweetsForUser(String username, String searchString);

	/**
	 * Adds a new tweet to a user timeline
	 * 
	 * @param username The author of the tweet
	 * @param contents The contents of the tweet
	 * @return The tweet just created
	 */
	Tweet addTweet(String username, String contents);

}