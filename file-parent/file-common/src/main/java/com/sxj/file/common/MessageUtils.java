package com.sxj.file.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sxj.util.common.StringUtils;

public class MessageUtils {

	private static Properties properties = new Properties();

	private static String path = "/config/eqtconfig.properties";

	static {
		InputStream inputFile;
		inputFile = MessageUtils.class.getResourceAsStream(path);
		try {
			properties.load(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getMessage(String key) {
		if (StringUtils.isNotEmpty(key)) {
			String message = properties.getProperty(key);
			return message;
		}
		return "";
	}

}
