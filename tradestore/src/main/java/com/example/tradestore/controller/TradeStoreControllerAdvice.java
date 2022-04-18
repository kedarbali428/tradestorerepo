package com.example.tradestore.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.tradestore.helper.ResponseBean;
import com.example.tradestore.helper.TradeStoreServiceException;


public class TradeStoreControllerAdvice extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = { TradeStoreServiceException.class })
	protected ResponseEntity<?> handleConflict(RuntimeException ex, WebRequest request) {
		TradeStoreServiceException exception = (TradeStoreServiceException) ex;	
		if(null != exception.getErrorBean() && null != exception.getPayload()){
			return handleExceptionInternal(ex, new ResponseBean(exception.getPayload(), exception.getErrorBean()), new HttpHeaders(), exception.getCode(), request);
		}else if (null != exception.getErrorBean()){
			return handleExceptionInternal(ex, exception.getErrorBean(), new HttpHeaders(), exception.getCode(),request);
		}else if (null != exception.getPayload()){
			return handleExceptionInternal(ex, exception.getPayload(), new HttpHeaders(), exception.getCode(),request);
		}else{
			return handleExceptionInternal(ex, exception.getMessage(), new HttpHeaders(), exception.getCode(),request);
		}
	}

}
