package com.luis.twitter.repository.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistsException(String username) {
		super("The user ´" + username + "´ already exist.");
	}
}
