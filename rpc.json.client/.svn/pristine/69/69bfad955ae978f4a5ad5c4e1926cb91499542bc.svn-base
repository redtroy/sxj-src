package com.xwtech.rpc.json.client.log4j;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class JsonRpcLayout extends Layout {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void activateOptions() {

	}

	@Override
	public String format(LoggingEvent arg0) {
		JsonRpcEvent event = (JsonRpcEvent) arg0.getMessage();
		String sql = "insert into T_RPC_LOG (F_LOG_ID,F_SENT_MSG,F_RECEIVED_MSG,F_EVENT_TIME,F_EXCEPTION) values (";
		sql += "'" + UUID.randomUUID().toString() + "',";
		sql += "'" + event.getSentMsg() + "'" + ",";
		sql += "'" + event.getReceivedMsg() + "'" + ",";
		sql += "to_date('" + sdf.format(event.getEventTime())
				+ "','YYYY-MM-dd HH24:mi:ss'),";
		sql += "'" + event.getException() + "')";
		return sql;
	}

	@Override
	public boolean ignoresThrowable() {
		return true;
	}

}
