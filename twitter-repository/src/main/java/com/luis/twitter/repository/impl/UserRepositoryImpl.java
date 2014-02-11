package com.luis.twitter.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luis.twitter.model.User;
import com.luis.twitter.repository.UserRepository;
import com.luis.twitter.repository.exceptions.UserNotFoundException;

@Repository
@Transactional
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository {

	@Override
	protected Object getTableName() {
		return "users";
	}

	@Override
	public User addUser(User user) {
		super.save("INSERT INTO users (username, name) VALUES (:username, :name) ", user);
		return findByUsername(user.getUsername());
	}

	@Override
	public User findByUsername(String username) {
		String query = "SELECT * FROM users where username = :username";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("username", username);

		List<User> users = getJdbcTemplate().query(query, parameters, getRowMapper());

		if (users == null || users.isEmpty()) {
			throw new UserNotFoundException(username);
		}
		return users.get(0);
	}

}