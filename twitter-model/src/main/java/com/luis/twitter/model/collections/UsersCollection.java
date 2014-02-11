package com.luis.twitter.model.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.luis.twitter.model.User;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersCollection {

	@XmlElement(name = "user", type = User.class)
	private List<User> users;

	public UsersCollection() {
	}

	public UsersCollection(Collection<User> collection) {
		users = Collections.unmodifiableList((List<User>) collection);
	}

	public List<User> getTweets() {
		return users;
	}

	public void setTweets(List<User> tweets) {
		this.users = tweets;
	}
}
