package com.luis.twitter.api;

public interface SecurityController {

	String login();

	void logout(String token);

}
