package com.sxj.util.exception;

/**
 * 业务异常类
 * 
 * @author dujinxin
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 8296584679267569883L;

	private String errorId;

	private String errorMessage;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String errorId, String errorMessage) {
		this.errorId = errorId;
		this.errorMessage = errorMessage;
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
