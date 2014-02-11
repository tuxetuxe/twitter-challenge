package com.luis.twitter.api.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luis.twitter.api.SecurityController;
import com.luis.twitter.repository.impl.AuthenticationTokenRepositoryImpl;

@Controller
@Transactional
@RequestMapping(value = "/security", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
		MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class SecurityControllerImpl implements SecurityController {

	@Autowired
	private AuthenticationTokenRepositoryImpl tokenRepository;

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public @ResponseBody String login() {
		return tokenRepository.addToken(UUID.randomUUID().toString());
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "/logout")
	public void logout(@RequestParam String token) {
		tokenRepository.deleteToken(token);
	}

}
