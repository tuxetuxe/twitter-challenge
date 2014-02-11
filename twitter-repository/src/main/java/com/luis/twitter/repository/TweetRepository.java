package com.luis.twitter.repository;

import java.util.List;

import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.User;

public interface TweetRepository extends BaseRepository<Tweet> {

	List<Tweet> timelineTweetsForUser(User user);

	List<Tweet> timelineTweetsForUser(User user, String searchString);

	Tweet addTweet(String contents, User user);

}