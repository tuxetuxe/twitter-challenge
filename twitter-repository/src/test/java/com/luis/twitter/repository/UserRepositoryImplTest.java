package com.luis.twitter.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.luis.twitter.model.User;

public class UserRepositoryImplTest extends BaseRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testAddUser() {
		User user = new User();
		user.setUsername("test.user.addUser");
		user.setName("test user addUser");
		user = userRepository.addUser(user);

		Assert.assertEquals(1, countRowsInTable("users"));
	}

	@Test
	public void testfindByUsername() {
		User user = new User();
		user.setUsername("test.user.findByUsername");
		user.setName("test user findByUsername");
		user = userRepository.addUser(user);

		User foundUser = userRepository.findByUsername(user.getUsername());
		Assert.assertEquals(user.getUsername(), foundUser.getUsername());
		Assert.assertEquals(user.getName(), foundUser.getName());
		Assert.assertEquals(user.getId(), foundUser.getId());
	}

}
