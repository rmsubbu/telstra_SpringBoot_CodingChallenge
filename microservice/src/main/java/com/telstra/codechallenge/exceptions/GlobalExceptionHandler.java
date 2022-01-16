package com.telstra.codechallenge.exceptions;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;

import com.telstra.codechallenge.constants.CommonConstants;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(TypeMismatchException e, ServletWebRequest request) {

		final Map<String, Object> result = new LinkedHashMap<>();
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_TIMESTAMP, new Date());

		if (request != null)
			result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_PATH, request.getRequest().getRequestURI());

		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_STATUS, HttpStatus.BAD_REQUEST.value());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_ERROR, HttpStatus.BAD_REQUEST.getReasonPhrase());
		log.info(CommonConstants.MESSAGE_ERROR, e.getMessage());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_MESSAGE, "Method arguments not valid exception");

		return result;
	}

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handle(ConstraintViolationException e, ServletWebRequest request) {

		final Map<String, Object> result = new LinkedHashMap<>();
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_TIMESTAMP, new Date());

		if (request != null)
			result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_PATH, request.getRequest().getRequestURI());

		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_STATUS, HttpStatus.BAD_REQUEST.value());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_ERROR, HttpStatus.BAD_REQUEST.getReasonPhrase());
		log.info(CommonConstants.MESSAGE_ERROR, e.getMessage());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_MESSAGE, "Constraint violation exception occurred");

		return result;
	}

	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnexpectedTypeException.class)
	public @ResponseBody Map<String, Object> handleUnexpectedTypeException(UnexpectedTypeException e,
			ServletWebRequest request) {
		// emulate Spring DefaultErrorAttributes
		return buildRequestException(e, request, "Unexpected type exception occurred", HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @param request
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(SearchResultExceededException.class)
	public @ResponseBody Map<String, Object> handleSearchResultExceededException(SearchResultExceededException e,
			ServletWebRequest request) {
		// emulate Spring DefaultErrorAttributes
		return buildRequestException(e, request, e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public @ResponseBody Map<String, Object> handleException(Exception exception, ServletWebRequest request) {
		// emulate Spring DefaultErrorAttributes
		return buildRequestException(exception, request, "Internal server error occurred",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private @ResponseBody Map<String, Object> buildRequestException(Exception exception, ServletWebRequest request,
			String message, HttpStatus httpStatus) {
		final Map<String, Object> result = new LinkedHashMap<>();
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_TIMESTAMP, new Date());

		if (request != null)
			result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_PATH, request.getRequest().getRequestURI());

		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_STATUS, httpStatus.value());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_ERROR, httpStatus.getReasonPhrase());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_ERRORS, exception.getMessage());
		result.put(CommonConstants.LOGS_GLOBAL_EXCEPTION_HANDLER_MESSAGE, message);
		return result;
	}
}
