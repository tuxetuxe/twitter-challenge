package com.luis.twitter.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A model representing a list of tweets.
 * When serialized the tag name will be "tweets"
 * Note: This class only exists to be serialized correctly by xml/json/... serializers (ie: jackson)
 * 
 * @author luis
 *
 */
@XmlRootElement(name = "tweets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TweetList {

	@XmlElement(name = "tweet", type = Tweet.class)
	private List<Tweet> tweets;

	public TweetList() {
	}

	public TweetList(List<Tweet> collection) {
		tweets = new ArrayList<>(collection);
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
}
