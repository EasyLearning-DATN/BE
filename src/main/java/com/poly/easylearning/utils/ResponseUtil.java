package com.poly.easylearning.utils;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
	public static Pageable pageable(int currentPage, int limitPage) {
		if(currentPage <= 0){
			currentPage = 1;
		}
		return PageRequest.of(currentPage - 1, limitPage);
	}
}
