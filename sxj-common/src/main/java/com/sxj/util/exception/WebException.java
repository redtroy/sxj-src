package com.sxj.util.exception;

import java.io.Serializable;

/**
 * WEB异常处理类
 * 
 * @author dujinxin
 *
 */
public class WebException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4234646749811032868L;

	/**
	 * 获取Web异常信息
	 * 
	 */
	public WebException() {
		super();
	}

	/**
	 * 获取Web异常信息
	 * 
	 * @param message
	 */
	public WebException(String message) {
		super(message);
		setErrorMessage(message);
	}

	/**
	 * 获取Web异常信息
	 * 
	 * @param message
	 * @param t
	 */
	public WebException(String message, Exception t) {
		super(message, t);
		setErrorMessage(message);
	}

	/**
	 * 获取Web异常信息
	 * 
	 * @param t
	 */
	public WebException(Exception t) {
		super(t);
		setErrorMessage(t.getMessage());
	}

	/**
	 * 获取Web异常信息
	 * 
	 * @param errorId
	 * @param message
	 */
	public WebException(String errorId, String message) {
		super(message, new Exception(errorId));
		setErrorId(errorId);
		setErrorMessage(message);
		System.out.println(errorId + "++++++++++++++++++++" + message);
	}

	private String errorId = "-1";
	private String errorMessage;

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
