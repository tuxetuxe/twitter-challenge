package com.luis.twitter.repository.exceptions;

import java.util.List;

public class NotASingleResultException extends RuntimeException {

	public NotASingleResultException(String type, List<?> list) {
		super("Expecting a sinlge " + type + " but got " + (list != null ? list.size() : " null list"));
	}

	private static final long serialVersionUID = 1L;

}
