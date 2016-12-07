package com.exfantasy.utils.http;

import org.apache.http.HttpStatus;

public class HttpUtilException extends Exception {
	private static final long serialVersionUID = -6241667447139322373L;
	
	public static final int COMMUNICATE_ERROR = 400;
	public static final int LOGIN_FAILED = 401;
	public static final int UNKNOWN_ERROR = 999;
	
	private int httpStatusCode;
	private int errorCode;

	public HttpUtilException() {
	}

	public HttpUtilException(String message, int httpStatusCode) {
		super(message);
		this.httpStatusCode = httpStatusCode;
		if (httpStatusCode == HttpStatus.SC_FORBIDDEN) {
			this.errorCode = LOGIN_FAILED;
		}
		else {
			this.errorCode = UNKNOWN_ERROR;
		}
	}

	public HttpUtilException(Throwable cause) {
		super(cause);
	}

	public HttpUtilException(String message, Throwable cause, int errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public HttpUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public int getHttpStatusCode() {
		return this.httpStatusCode;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
}
