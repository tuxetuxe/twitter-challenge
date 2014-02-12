package com.luis.twitter.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luis.twitter.model.User;
import com.luis.twitter.model.UserFollowing;
import com.luis.twitter.repository.UserFollowingRepository;
import com.luis.twitter.repository.exceptions.UserAlreadyFollowedException;
import com.luis.twitter.repository.exceptions.UserNotFollowedException;
import com.luis.twitter.repository.exceptions.UserNotFoundException;

@Repository
@Transactional
public class UserFollowingRepositoryImpl extends BaseRepositoryImpl<UserFollowing> implements UserFollowingRepository {

	private RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

	@Override
	protected String getTableName() {
		return "user_following";
	}

	@Override
	public UserFollowing startFollowing(User user, User followingUser) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(followingUser);

		String query = "INSERT INTO user_following (user_id,following_id) VALUES ( :user_id, :following_id )";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", user.getId());
		parameters.put("following_id", followingUser.getId());
		try {
			getJdbcTemplate().update(query, parameters);
			return findUserFollowing(user, followingUser);
		} catch (DuplicateKeyException e) {
			throw new UserAlreadyFollowedException(user, followingUser);
		}
	}

	@Override
	public UserFollowing stopFollowing(User user, User followingUser) {
		Objects.requireNonNull(user);
		Objects.requireNonNull(followingUser);

		List<User> followedUsers = findWhoTheUserFollows(user);
		if (!followedUsers.contains(followingUser)) {
			throw new UserNotFollowedException(user, followingUser);
		}
		
		UserFollowing userFollowingDeleted = findUserFollowing(user, followingUser);

		String query = "DELETE FROM user_following WHERE user_id = :user_id AND following_id = :following_id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", user.getId());
		parameters.put("following_id", followingUser.getId());
		getJdbcTemplate().update(query, parameters);

		return userFollowingDeleted;
	}

	@Override
	public List<User> findWhoTheUserFollows(User user) {
		String query = "SELECT f.id, f.name, f.username " + //
				" FROM users u " + //
				"     INNER JOIN user_following uf ON u.id = uf.user_id " + //
				"     INNER JOIN users f ON uf.following_id = f.id " + //
				" WHERE u.id = :user_id";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", user.getId());

		List<User> followedUsers = getJdbcTemplate().query(query, parameters, userRowMapper);
		return followedUsers;
	}

	@Override
	public List<User> findFollowersForUser(User user) {
		String query = "SELECT f.id, f.name, f.username " + //
				" FROM users u " + //
				"     INNER JOIN user_following uf ON u.id = uf.following_id " + //
				"     INNER JOIN users f ON uf.user_id = f.id " + //
				" WHERE u.id = :user_id";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", user.getId());

		List<User> followedUsers = getJdbcTemplate().query(query, parameters, userRowMapper);
		return followedUsers;
	}

	private UserFollowing findUserFollowing(User user, User followingUser) {
		String query = "SELECT * FROM user_following WHERE user_id = :user_id AND following_id = :following_id ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", user.getId());
		parameters.put("following_id", followingUser.getId());
		List<UserFollowing> userFollowings = getJdbcTemplate().query(query, parameters, getRowMapper());
		if (userFollowings == null || userFollowings.size() != 1) {
			throw new IllegalStateException();
		}
		return userFollowings.get(0);
	}

}