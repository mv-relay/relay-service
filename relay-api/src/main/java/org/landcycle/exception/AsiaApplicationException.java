package org.landcycle.exception;

/**
 * Custom Asia Exception
 * @author valerio
 *
 */
public class AsiaApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	String errorCode = "";

	public AsiaApplicationException(String msg) {
		super(msg);
	}

	public AsiaApplicationException(Exception e) {
		super(e);
	}

	public AsiaApplicationException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	public AsiaApplicationException(String errorCode, String msg ,Throwable cause) {
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
