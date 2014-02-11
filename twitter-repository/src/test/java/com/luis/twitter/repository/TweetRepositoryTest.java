package com.luis.twitter.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.User;

public class TweetRepositoryTest extends BaseRepositoryTest {

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserFollowingRepository userFollowingRepository;

	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setup() {
		user1 = new User();
		user1.setUsername("test.user.tweets_1");
		user1.setName("test user tweets #1");
		user1 = userRepository.addUser(user1);
		user2 = new User();
		user2.setUsername("test.user.tweets_2");
		user2.setName("test user tweets #2");
		user2 = userRepository.addUser(user2);
		user3 = new User();
		user3.setUsername("test.user.tweets_3");
		user3.setName("test user tweets #3");
		user3 = userRepository.addUser(user3);
	}

	@Test
	public void testTimelineTweetsForUserWithoutSearchCriteria() {

		String tweetContents = "Heeelloooo!";
		tweetRepository.addTweet(tweetContents, user1);
		tweetRepository.addTweet(tweetContents, user2);
		tweetRepository.addTweet(tweetContents, user3);

		Assert.assertEquals(3, countRowsInTable("tweets"));

		List<Tweet> tweetsWithoutFollowing = tweetRepository.timelineTweetsForUser(user1);
		Assert.assertEquals(1, tweetsWithoutFollowing.size());
		Assert.assertEquals(tweetContents, tweetsWithoutFollowing.get(0).getContents());
		Assert.assertEquals(user1.getId(), tweetsWithoutFollowing.get(0).getAuthorId());
		Assert.assertTrue(tweetsWithoutFollowing.get(0).getCreationDate().after(tweetsWithoutFollowing.get(1).getCreationDate()));

		userFollowingRepository.startFollowing(user1, user2);
		userFollowingRepository.startFollowing(user2, user3);

		List<Tweet> tweetsWithFollowing = tweetRepository.timelineTweetsForUser(user1);
		Assert.assertEquals(2, tweetsWithFollowing.size());
		Assert.assertEquals(tweetContents, tweetsWithFollowing.get(0).getContents());
		Assert.assertEquals(user2.getId(), tweetsWithFollowing.get(0).getAuthorId());

		Assert.assertEquals(tweetContents, tweetsWithFollowing.get(1).getContents());
		Assert.assertEquals(user1.getId(), tweetsWithFollowing.get(1).getAuthorId());
		Assert.assertTrue(tweetsWithFollowing.get(0).getCreationDate().after(tweetsWithoutFollowing.get(1).getCreationDate()));

	}

	@Test
	public void testTimelineTweetsForUserWitSearchCriteria() {

		String helloEnglish = "Hello!";
		String helloSwedish = "hej!";
		String helloEnglishAndSwedish = "Hello == hej!";
		tweetRepository.addTweet(helloEnglish, user1);
		tweetRepository.addTweet(helloEnglishAndSwedish, user2);
		tweetRepository.addTweet(helloSwedish, user1);

		userFollowingRepository.startFollowing(user1, user2);

		Assert.assertEquals(3, countRowsInTable("tweets"));

		List<Tweet> tweetsWithHelloEnglish = tweetRepository.timelineTweetsForUser(user1, "hello");
		Assert.assertEquals(2, tweetsWithHelloEnglish.size());
		Assert.assertEquals(helloEnglishAndSwedish, tweetsWithHelloEnglish.get(0).getContents());
		Assert.assertEquals(user2.getId(), tweetsWithHelloEnglish.get(0).getAuthorId());
		Assert.assertEquals(helloEnglish, tweetsWithHelloEnglish.get(1).getContents());
		Assert.assertEquals(user1.getId(), tweetsWithHelloEnglish.get(1).getAuthorId());

		List<Tweet> tweetsWithHelloSwedish = tweetRepository.timelineTweetsForUser(user1, "hej");
		Assert.assertEquals(2, tweetsWithHelloSwedish.size());
		Assert.assertEquals(helloSwedish, tweetsWithHelloSwedish.get(0).getContents());
		Assert.assertEquals(user1.getId(), tweetsWithHelloSwedish.get(0).getAuthorId());
		Assert.assertEquals(helloEnglishAndSwedish, tweetsWithHelloSwedish.get(1).getContents());
		Assert.assertEquals(user2.getId(), tweetsWithHelloSwedish.get(1).getAuthorId());

	}

	@Test
	public void testAddTweet() {
		String contents = "test contents";
		Tweet addedTweet = tweetRepository.addTweet(contents, user1);
		Assert.assertEquals(1, countRowsInTable("tweets"));
		Assert.assertEquals(contents, addedTweet.getContents());
		Assert.assertEquals(user1.getId(), addedTweet.getAuthorId());
	}

}
