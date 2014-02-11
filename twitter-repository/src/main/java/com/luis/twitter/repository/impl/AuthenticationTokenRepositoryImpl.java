package com.luis.twitter.repository.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationTokenRepositoryImpl {

	private Map<String, String> tokens = new ConcurrentHashMap<>();

	public String addToken(String token) {
		tokens.put(token, token);
		return token;
	}

	public void deleteToken(String token) {
		tokens.remove(token);
	}

	public boolean isTokenAuthorized(String token) {
		return tokens.containsKey(token);
	}
}
