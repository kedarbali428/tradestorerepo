package com.example.tradestore.helper;

import java.io.Serializable;


public class ResponseBean implements Serializable {

	/**
	 * This is a bean to hold the response of the service call respnse object and 
	 * 
	 */
	private static final long serialVersionUID = -7395224996453999026L;
	
	private transient Object payload;
	private ErrorBean errorBean;
	
	public ResponseBean() {
		super();
	}

	public ResponseBean(Object payload, ErrorBean errorBean) {
		super();
		this.payload = payload;
		this.errorBean = errorBean;
	}
	
	public ResponseBean(Object payload) {
		super();
		this.payload = payload;
	}

	public ResponseBean(ErrorBean errorBean) {
		super();
		this.errorBean = errorBean;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public ErrorBean getErrorBean() {
		return errorBean;
	}

	public void setErrorBean(ErrorBean errorBean) {
		this.errorBean = errorBean;
	}

	@Override
	public String toString() {
		return "ResponseBean [payload=" + payload + ", errorBean=" + errorBean + "]";
	}

}
