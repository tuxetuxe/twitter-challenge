package com.luis.twitter.repository;

import java.util.List;

import com.luis.twitter.model.User;
import com.luis.twitter.model.UserFollowing;

public interface UserFollowingRepository extends BaseRepository<UserFollowing> {

	void startFollowing(User user, User followingUser);

	void stopFollowing(User user, User followingUser);

	List<User> findWhoTheUserFollows(User user);

	List<User> findFollowersForUser(User user);

}