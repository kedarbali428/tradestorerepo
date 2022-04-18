package com.example.tradestore.helper;

import java.io.Serializable;

public class ErrorBean implements Serializable {

	/**
	 * This class is used for holding the error code and message in case of error response from endpoint 
	 * 
	 */
	private static final long serialVersionUID = -1152402161037722939L;
	
	private String errorCode;
	private String errorMessage;
	
	public ErrorBean(){
		//Empty constructor
	}
	
	public ErrorBean(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString(){
		return "errorCode : "+errorCode+", errorMessage: "+errorMessage;
	}
}
