package com.luis.twitter.api;

import com.luis.twitter.model.User;
import com.luis.twitter.model.UsersList;

public interface UserController {

	User addUser(String username, String name);

	User getUserInfo(String username);

	UsersList getFollowersForUser(String username);

	UsersList findWhoTheUserFollows(String username);

	Boolean startFollowingUser(String username, String otherUsername);

	Boolean stopFollowingUser(String username, String otherUsername);

}