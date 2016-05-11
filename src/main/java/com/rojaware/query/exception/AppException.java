package com.rojaware.query.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = 5037087348985035265L;
	protected String type;

	public AppException(String p_exceptionMsg, String type) {
		super(p_exceptionMsg);
		this.type = type;
	}

	public AppException(String p_exceptionMsg){
		super(p_exceptionMsg);
	}

	public AppException(String p_exceptionMsg, Throwable cause){
		super(p_exceptionMsg, cause);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
