package org.landcycle.exception;

/**
 * Custom Asia Exception
 * @author valerio
 *
 */
public class AsiaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String errorCode = "";

	public AsiaException(String msg) {
		super(msg);
	}

	public AsiaException(Exception e) {
		super(e);
	}

	public AsiaException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	public AsiaException(String errorCode, String msg ,Throwable cause) {
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
