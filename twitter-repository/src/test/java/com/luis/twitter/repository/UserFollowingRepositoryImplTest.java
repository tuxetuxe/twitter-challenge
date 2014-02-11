package com.luis.twitter.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.luis.twitter.model.User;
import com.luis.twitter.repository.exceptions.UserAlreadyFollowedException;
import com.luis.twitter.repository.exceptions.UserNotFollowedException;

public class UserFollowingRepositoryImplTest extends BaseRepositoryTest {

	@Autowired
	private UserFollowingRepository userFollowingRepository;

	@Autowired
	private UserRepository userRepository;

	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setup() {
		user1 = new User();
		user1.setUsername("test.user_following_1");
		user1.setName("test user_following #");
		user1 = userRepository.addUser(user1);
		user2 = new User();
		user2.setUsername("test.user_following_2");
		user2.setName("test user_following #2");
		user2 = userRepository.addUser(user2);
		user3 = new User();
		user3.setUsername("test.user_following_3");
		user3.setName("test user_following #");
		user3 = userRepository.addUser(user3);
	}

	@Test
	public void testStartFollowing() {

		userFollowingRepository.startFollowing(user1, user2);
		userFollowingRepository.startFollowing(user2, user2);

		Assert.assertEquals(2, countRowsInTable("user_following"));
	}

	@Test
	public void testStartFollowingAlreadyFollowedUser() {

		userFollowingRepository.startFollowing(user1, user2);
		try {
			userFollowingRepository.startFollowing(user1, user2);
			Assert.fail("A user can not follow the same user twice!");
		} catch (UserAlreadyFollowedException e) {
			// everything ok!
		}
		Assert.assertEquals(1, countRowsInTable("user_following"));
	}

	@Test
	public void testStopFollowing() {

		userFollowingRepository.startFollowing(user1, user2);
		userFollowingRepository.stopFollowing(user1, user2);
		Assert.assertEquals(0, countRowsInTable("user_following"));
	}

	@Test
	public void testStopFollowingNotFolowedUser() {

		try {
			userFollowingRepository.stopFollowing(user1, user2);
			Assert.fail("A user can not stop following a user that is not followed");
		} catch (UserNotFollowedException e) {
			// everything ok!
		}
		Assert.assertEquals(0, countRowsInTable("user_following"));

		userFollowingRepository.startFollowing(user1, user2);
		try {
			userFollowingRepository.stopFollowing(user1, user3);
			Assert.fail("A user can not stop following a user that is not followed");
		} catch (UserNotFollowedException e) {
			// everything ok!
		}
		Assert.assertEquals(1, countRowsInTable("user_following"));
	}

	@Test
	public void testFindWhoTheUserFollows() {
		userFollowingRepository.startFollowing(user1, user2);
		userFollowingRepository.startFollowing(user1, user3);
		List<User> users = userFollowingRepository.findWhoTheUserFollows(user1);
		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
		Assert.assertTrue(users.contains(user2));
		Assert.assertTrue(users.contains(user3));
	}

	@Test
	public void testFindFollowersForUser() {
		userFollowingRepository.startFollowing(user1, user2);
		userFollowingRepository.startFollowing(user3, user2);
		List<User> users = userFollowingRepository.findFollowersForUser(user2);
		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
		Assert.assertTrue(users.contains(user1));
		Assert.assertTrue(users.contains(user3));
	}
}
