package com.poly.easylearning.mapper;

import com.poly.easylearning.dto.UserDTO;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.entity.UserInfo;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<User, UserDTO> {
	@Override
	public UserDTO apply(User user) {
		UserInfo userInfo = user.getUserInfo();
		return UserDTO.builder()
				.id(user.getId())
				.username(user.getUsername())
				.fullName(userInfo.getFullName())
				.email(userInfo.getEmail())
				.dayOfBirth(userInfo.getDayOfBirth())
				.userInfoId(userInfo.getId())
				.avatar(userInfo.getAvatar() != null ? userInfo.getAvatar().getUrl() : "")
				.build();
	}

	// For Admin manager
	public UserDTO applyForA(User user) {
		UserInfo userInfo = user.getUserInfo();
		return UserDTO.builder()
				.id(user.getId())
				.username(user.getUsername())
				.fullName(userInfo.getFullName())
				.email(userInfo.getEmail())
				.dayOfBirth(userInfo.getDayOfBirth())
				.provider(user.getProvider())
				.locked(user.isLocked())
				.userInfoId(userInfo.getId())
				.avatar(userInfo.getAvatar() != null ? userInfo.getAvatar().getUrl() : "")
				.build();
	}
}
