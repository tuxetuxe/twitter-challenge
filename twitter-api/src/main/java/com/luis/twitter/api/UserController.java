package com.luis.twitter.api;

import com.luis.twitter.model.User;
import com.luis.twitter.model.UserFollowing;
import com.luis.twitter.model.UsersList;
/**
 * A RESTfull controller that handles all the operations related with users
 * 
 * @author luis
 *
 */
public interface UserController {

	/**
	 * Created a new user in the system
	 * 
	 * @param username The user username
	 * @param name The user name
	 * @return The newly created user
	 */
	User addUser(String username, String name);

	/**
	 * Gets all the available user infor from a username
	 * 
	 * @param username The username of the user
	 * @return A User object with all the user info available
	 */
	User getUserInfo(String username);

	/**
	 * Gets all the users that follow a specified user
	 * 
	 * @param username The username of the user to search
	 * @return A list of users that follow the user specified in the parameter
	 */
	UsersList getFollowersForUser(String username);

	/**
	 * Gets all the users that a user follow
	 * 
	 * @param username The username of the user to search
	 * @return A list of users that the user specified in the parameter follow
	 */
	UsersList findWhoTheUserFollows(String username);

	/**
	 * Makes a user follow another user.
	 * 
	 * @param username The user that will follow
	 * @param otherUsername The user that will be followed
	 * @return The just created follow object
	 */
	UserFollowing startFollowingUser(String username, String otherUsername);

	/**
	 * Makes a user unfollow another user
	 * 
	 * @param username The user that will unfollow
	 * @param otherUsername The user that will be unfollowed
	 * @return The just deleted follow object
	 */
	UserFollowing stopFollowingUser(String username, String otherUsername);

	/**
	 * Lists all the users in the system
	 * 
	 * @return A list of all the users available in the system
	 */
	UsersList getAllUsers();

}