package com.luis.twitter.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.luis.twitter.api.UserController;
import com.luis.twitter.model.User;
import com.luis.twitter.model.collections.UsersCollection;
import com.luis.twitter.repository.UserFollowingRepository;
import com.luis.twitter.repository.UserRepository;

@Controller
@Transactional
@RequestMapping(value = "/users", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
		MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserControllerImpl implements UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFollowingRepository userFollowingRepository;

	@Override
	@RequestMapping(method = RequestMethod.PUT, value = "/{username}")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody
	User addUser(@PathVariable String username, @RequestParam String name) {
		User user = new User();
		user.setUsername(username);
		user.setName(name);
		return userRepository.addUser(user);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/{username}")
	public @ResponseBody
	User getUserInfo(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/{username}/followers")
	public @ResponseBody
	UsersCollection getFollowersForUser(@PathVariable String username) {
		Assert.notNull(username);
		User user = userRepository.findByUsername(username);
		return new UsersCollection(userFollowingRepository.findFollowersForUser(user));
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, value = "/{username}/following")
	public @ResponseBody
	UsersCollection findWhoTheUserFollows(@PathVariable String username) {
		Assert.notNull(username);
		User user = userRepository.findByUsername(username);
		return new UsersCollection(userFollowingRepository.findWhoTheUserFollows(user));
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "/{username}/follow/{otherUsername}")
	public @ResponseBody
	Boolean startFollowingUser(@PathVariable String username, @PathVariable String otherUsername) {
		Assert.notNull(username);
		Assert.notNull(otherUsername);
		User user = userRepository.findByUsername(username);
		User otherUser = userRepository.findByUsername(otherUsername);
		userFollowingRepository.startFollowing(user, otherUser);
		return true;
	}

	@Override
	@RequestMapping(method = RequestMethod.DELETE, value = "/{username}/follow/{otherUsername}")
	public @ResponseBody
	Boolean stopFollowingUser(@PathVariable String username, @PathVariable String otherUsername) {
		Assert.notNull(username);
		Assert.notNull(otherUsername);
		User user = userRepository.findByUsername(username);
		User otherUser = userRepository.findByUsername(otherUsername);
		userFollowingRepository.stopFollowing(user, otherUser);
		return true;
	}

}
