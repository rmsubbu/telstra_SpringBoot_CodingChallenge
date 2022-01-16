package com.telstra.codechallenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SearchResultExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SearchResultExceededException() {
		super("Search Result cannot exceed 30 as Github API response limits are set to 30");
	}
}
