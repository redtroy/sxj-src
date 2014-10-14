package com.sxj.file.fastdfs;

public class DfsConfig {

	private Integer connect_timeout;

	private Integer network_timeout;

	private String charset;

	private Integer tracker_http_port;

	private String anti_steal_token;

	private String secret_key;
	
	public Integer getConnect_timeout() {
		return connect_timeout;
	}

	public void setConnect_timeout(Integer connect_timeout) {
		this.connect_timeout = connect_timeout;
	}

	public Integer getNetwork_timeout() {
		return network_timeout;
	}

	public void setNetwork_timeout(Integer network_timeout) {
		this.network_timeout = network_timeout;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Integer getTracker_http_port() {
		return tracker_http_port;
	}

	public void setTracker_http_port(Integer tracker_http_port) {
		this.tracker_http_port = tracker_http_port;
	}

	public String getAnti_steal_token() {
		return anti_steal_token;
	}

	public void setAnti_steal_token(String anti_steal_token) {
		this.anti_steal_token = anti_steal_token;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

}
