package com.example.tradestore.helper;

import org.springframework.http.HttpStatus;


public class TradeStoreServiceException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2021090955654674543L;
	
	private HttpStatus code;
	private ErrorBean errorBean;
	private Object payload;
	
	public TradeStoreServiceException() {
		super();
		
	}
	
	public TradeStoreServiceException(HttpStatus code, ErrorBean errorBean) {
		super();
		this.code = code;
		this.errorBean = errorBean;
	}
	
	
	public TradeStoreServiceException(HttpStatus code, ErrorBean errorBean, Object payload) {
		super();
		this.code = code;
		this.errorBean = errorBean;
		this.payload = payload;
	}
	
	public TradeStoreServiceException(HttpStatus code, ErrorBean errorBean, Object payload, Throwable cause) {
		super(cause);
		this.code = code;
		this.errorBean = errorBean;
		this.payload = payload;
	}
	
	public HttpStatus getCode() {
		return code;
	}

	public ErrorBean getErrorBean() {
		return errorBean;
	}

	public Object getPayload() {
		return payload;
	}
	
	
}
