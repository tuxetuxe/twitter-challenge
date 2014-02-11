package com.luis.twitter.api;

import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.collections.TweetCollection;

public interface TweetController {

	TweetCollection getTweetsForUser(String username, String searchString);

	Tweet addTweet(String username, String contents);

}