package com.luis.twitter.api;

import com.luis.twitter.model.User;
import com.luis.twitter.model.collections.UsersCollection;

public interface UserController {

	User addUser(String username, String name);

	User getUserInfo(String username);

	UsersCollection getFollowersForUser(String username);

	UsersCollection findWhoTheUserFollows(String username);

	Boolean startFollowingUser(String username, String otherUsername);

	Boolean stopFollowingUser(String username, String otherUsername);

}