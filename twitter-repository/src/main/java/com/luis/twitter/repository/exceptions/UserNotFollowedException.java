package com.luis.twitter.repository.exceptions;

import com.luis.twitter.model.User;

public class UserNotFollowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserNotFollowedException(User user, User followingUser) {
		super("User '" + user.getUsername() + "' does not follow '" + followingUser.getUsername() + "'");
	}

}
