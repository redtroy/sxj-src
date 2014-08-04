package com.xwtech.rpc.json.client.log4j;

import java.util.Date;

public class JsonRpcEvent {

	private Date eventTime;
	private String sentMsg;
	private String receivedMsg;
	private String exception;

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public String getSentMsg() {
		if (sentMsg == null)
			return "";
		return sentMsg;
	}

	public void setSentMsg(String sentMsg) {
		this.sentMsg = sentMsg;
	}

	public String getReceivedMsg() {
		if (receivedMsg == null)
			return receivedMsg;
		return receivedMsg;
	}

	public void setReceivedMsg(String receivedMsg) {
		this.receivedMsg = receivedMsg;
	}

	public String getException() {
		if (exception == null)
			return "";
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		return "JsonRpcEvent [eventTime=" + eventTime + ", sentMsg=" + sentMsg
				+ ", receivedMsg=" + receivedMsg + ", exception=" + exception
				+ "]";
	}

}
