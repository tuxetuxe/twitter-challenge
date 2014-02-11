package com.luis.twitter.repository;

import com.luis.twitter.model.User;

public interface UserRepository extends BaseRepository<User> {

	User addUser(User user);

	User findByUsername(String username);

}