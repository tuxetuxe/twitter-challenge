package com.luis.twitter.api;

/**
 * A RESTfull controller that handles the login and logout security operations
 * 
 * @author luis
 *
 */
public interface SecurityController {

	/**
	 * Issues a token that has to be used in all consequent request for them to be authorised
	 * 
	 * @return An authentication token
	 */
	String login();

	/**
	 * Invalidates the authentication token. It can no longer be used.
	 * 
	 * @param token The Authentication token to be invalidated
	 */
	void logout(String token);

}
