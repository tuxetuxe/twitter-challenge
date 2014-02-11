package com.luis.twitter.api;

import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.TweetList;

public interface TweetController {

	TweetList getTweetsForUser(String username, String searchString);

	Tweet addTweet(String username, String contents);

}