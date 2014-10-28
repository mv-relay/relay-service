package org.landcycle.api.exception;

public class LandcycleException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String errorCode = "";

	public LandcycleException(String msg) {
		super(msg);
	}

	public LandcycleException(Exception e) {
		super(e);
	}

	public LandcycleException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public LandcycleException(String errorCode, String msg, Throwable cause) {
		super(msg, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
