package com.luis.twitter.api.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luis.twitter.repository.exceptions.UserAlreadyExistsException;
import com.luis.twitter.repository.exceptions.UserAlreadyFollowedException;
import com.luis.twitter.repository.exceptions.UserNotFollowedException;
import com.luis.twitter.repository.exceptions.UserNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFound(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Unknown user.", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	protected ResponseEntity<Object> handleUserAlreadyExists(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, "User already exists.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UserNotFollowedException.class)
	protected ResponseEntity<Object> handleUserNotFollowed(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, "The user is not followed.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UserAlreadyFollowedException.class)
	protected ResponseEntity<Object> handleUserAlreadyFollowed(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, "The user is already followed.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Invalid input provided.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
