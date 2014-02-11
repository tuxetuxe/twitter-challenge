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

import com.luis.twitter.api.TweetController;
import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.TweetList;
import com.luis.twitter.model.User;
import com.luis.twitter.repository.TweetRepository;
import com.luis.twitter.repository.UserRepository;

@Controller
@Transactional
@RequestMapping(value = "/tweets", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
		MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class TweetControllerImpl implements TweetController {

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = RequestMethod.GET, value = "/{username:.*}/timeline")
	public @ResponseBody
	TweetList getTweetsForUser(@PathVariable String username, @RequestParam(value = "search", required = false) String searchString) {
		Assert.notNull(username);
		User user = userRepository.findByUsername(username);
		return new TweetList(tweetRepository.timelineTweetsForUser(user, searchString));
	}

	@Override
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.PUT, value = "/{username:.*}")
	public @ResponseBody
	Tweet addTweet(@PathVariable String username, @RequestParam String contents) {
		Assert.notNull(username);
		Assert.hasText(contents);
		User user = userRepository.findByUsername(username);
		return tweetRepository.addTweet(contents, user);
	}

}
