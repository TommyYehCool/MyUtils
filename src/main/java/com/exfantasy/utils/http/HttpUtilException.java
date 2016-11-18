package com.exfantasy.utils.http;

public class HttpUtilException extends Exception {
	private static final long serialVersionUID = -6241667447139322373L;

	public HttpUtilException() {
	}

	public HttpUtilException(String message) {
		super(message);
	}

	public HttpUtilException(Throwable cause) {
		super(cause);
	}

	public HttpUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
