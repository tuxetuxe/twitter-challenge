package com.luis.twitter.model.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.luis.twitter.model.Tweet;

@XmlRootElement(name = "tweets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TweetCollection {

	@XmlElement(name = "tweet", type = Tweet.class)
	private List<Tweet> tweets;

	public TweetCollection() {
	}

	public TweetCollection(Collection<Tweet> collection) {
		tweets = Collections.unmodifiableList((List<Tweet>) collection);
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
}
