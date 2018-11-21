package org.patomtz.restfulws.utm.rest.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.patomtz.restfulws.utm.model.ErrorInfo;
import org.patomtz.restfulws.utm.model.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Errors handleNotFound(HttpServletRequest req, HttpServletResponse res, ResourceNotFoundException e) {
		Errors errors = new Errors();
		errors.addError(new ErrorInfo(req.getRequestURL().toString(), HttpStatus.NOT_FOUND.toString(), e));
		return errors;
	}

	@ExceptionHandler(MethodNotAllowedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public Errors handleNotAllowed(HttpServletRequest req, HttpServletResponse res, MethodNotAllowedException e) {
		Errors errors = new Errors();
		errors.addError(new ErrorInfo(req.getRequestURL().toString(), HttpStatus.METHOD_NOT_ALLOWED.toString(), e));
		return errors;
	}
}