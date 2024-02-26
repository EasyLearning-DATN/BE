package com.poly.easylearning.utils;

import com.poly.easylearning.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityContextUtils {
	public static User getCurrentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(Objects.isNull(authentication)){
			return null;
		}
		return (User)authentication.getPrincipal();
	}
}
