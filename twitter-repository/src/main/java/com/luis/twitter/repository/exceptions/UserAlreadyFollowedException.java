package com.luis.twitter.repository.exceptions;

import com.luis.twitter.model.User;

public class UserAlreadyFollowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserAlreadyFollowedException(User user, User followingUser) {
		super("User '" + user.getUsername() + "' already followes '" + followingUser.getUsername() + "'");
	}

}
