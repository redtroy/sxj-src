package com.sxj.supervisor.tasks.Model;

import java.util.Map;

public class DataMap {
	private Map<String, String> name;
	private Map<String, Map<String, Map<String, String>>> data;

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public Map<String, Map<String, Map<String, String>>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, Map<String, String>>> data) {
		this.data = data;
	}

}
