package com.luis.twitter.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersList {

	@XmlElement(name = "user", type = User.class)
	private List<User> users;

	public UsersList() {
	}

	public UsersList(List<User> collection) {
		users = new ArrayList<>(collection);
	}

	public List<User> getTweets() {
		return users;
	}

	public void setTweets(List<User> tweets) {
		this.users = tweets;
	}
}
