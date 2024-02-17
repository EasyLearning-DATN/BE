package com.poly.easylearning.utils;

import org.springframework.stereotype.Component;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

@Component
public class ResponseUtil {
	public String getMessageBundle(String key) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
		return resourceBundle.getString(key);
	}

	public long currentTimeSeconds() {
		long currentTimeMillis = System.currentTimeMillis();
		return TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis);
	}
}
