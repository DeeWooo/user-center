package com.liyou.uc.user.web;

import com.liyou.uc.model.UcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ivywooo
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public UcResponse exceptionHandler(Exception e, HttpServletResponse response) {
		logger.error("", e);
		return UcResponse.failure(e);
	}

}
