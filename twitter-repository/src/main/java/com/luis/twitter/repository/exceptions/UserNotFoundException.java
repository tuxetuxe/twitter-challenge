package com.luis.twitter.repository.exceptions;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String username) {
		super("The user ´" + username + "´ does not exist.");
	}
}
