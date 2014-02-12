package com.luis.twitter.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A model representing a list of users.
 * When serialized the tag name will be "users"
 * Note: This class only exists to be serialized correctly by xml/json/... serializers (ie: jackson)
 * 
 * @author luis
 *
 */
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
