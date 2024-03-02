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
				.username(user.getUsername())
				.fullName(userInfo.getFullName())
				.email(userInfo.getEmail())
				.dayOfBirth(userInfo.getDayOfBirth())
				.avatar(userInfo.getAvatar() != null ? userInfo.getAvatar().getUrl() : "")
				.build();
	}
}
