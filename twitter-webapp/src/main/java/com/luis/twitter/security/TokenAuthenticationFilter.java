package com.luis.twitter.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import com.luis.twitter.repository.impl.AuthenticationTokenRepositoryImpl;

/**
 * An authentication filter that discovers the "token" parameter and uses it as the authenticated principal
 * 
 * @author luis
 *
 */
public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Autowired
	private AuthenticationTokenRepositoryImpl authenticationTokenRepository;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		String token = request.getParameter("token");
		if (StringUtils.hasText(token) && authenticationTokenRepository.isTokenAuthorized(token)) {
			return token;
		} else {
			return null;
		}
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "api-user-authenticated-with-token";
	}
}
