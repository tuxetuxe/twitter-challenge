package com.luis.twitter.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * If control reaches this entry point the pre-authentication phased resulted in no authentication token being discovered.
 * So the request can only be UNAUTHORIZED.
 * s
 * @author luis
 *
 */
public class TwitterAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException,
			ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or unauthorized authentication token.");
	}
}
