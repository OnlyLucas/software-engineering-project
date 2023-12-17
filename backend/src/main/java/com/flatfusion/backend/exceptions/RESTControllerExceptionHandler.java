package com.flatfusion.backend.exceptions;

import com.flatfusion.backend.controllers.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This ExceptionHandler handles exceptions that occur during the handling of http requests.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RESTControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RESTControllerExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info("Timeout for Async Request. " + ex.toString(), request);
        return super.handleAsyncRequestTimeoutException(ex, headers, status, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
        logger.info("Mapping from request to Java Type failed. " + ex.toString(), request);
        return super.handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<Object> handleIllegalArgumentException(MethodArgumentTypeMismatchException ex, WebRequest request){
        logger.info("Invalid Argument in request. " + ex.toString(), request);
        return super.handleExceptionInternal(ex, "Invalid Argument.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    // TODO delete after debugging
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.info("Http message not readable. " + ex.toString(), request);
        return super.handleExceptionInternal(ex, "Invalid Argument.", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // HttpMessageNotReadableException is already handled by Spring ResponseEntityExceptionHandler
}