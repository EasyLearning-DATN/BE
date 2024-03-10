package com.poly.easylearning.utils;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.BaseEntity;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.RoleName;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.exception.InvalidUserException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ValidateUserUpdateUtils<T extends BaseEntity> {
	public static <T extends BaseEntity> void checkUserUpdate(T entity){
		User currentUser;
		UUID currentUserId;
		List<RoleName> roleName;
		boolean matchRoleAdmin = false;

		try {
			currentUser = SecurityContextUtils.getCurrentUser();
			currentUserId = currentUser.getId();
			roleName = currentUser.getUserRoles().stream().map(userRole -> userRole.getRole().getName()).toList();
		} catch (Exception e) {
			throw new DataNotFoundException(ResourceBundleConstant.USR_2019);
		}

		for (RoleName role : roleName) {
			if (role.equals(RoleName.ROLE_ADMIN)) {
				matchRoleAdmin = true;
				break;
			}
		}

		if (!(entity.getCreatedBy().equals(currentUserId) || matchRoleAdmin)) {
			throw new InvalidUserException(ResourceBundleConstant.USR_2020);
		}
	}
}
