package com.poly.easylearning.utils;


import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ResponseUtil {
	public static String getMessageBundle(String key) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
		return resourceBundle.getString(key);
	}

	public static long currentTimeSeconds() {
		long currentTimeMillis = System.currentTimeMillis();
		return TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis);
	}
}
