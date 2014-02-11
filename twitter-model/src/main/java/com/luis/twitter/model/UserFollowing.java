package com.luis.twitter.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="userFollowing")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserFollowing extends BaseDomain {

	public Long userId;
	public Long following_Id;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFollowing_Id() {
		return following_Id;
	}

	public void setFollowing_Id(Long following_Id) {
		this.following_Id = following_Id;
	}

}
