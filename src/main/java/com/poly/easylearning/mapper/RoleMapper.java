package com.poly.easylearning.mapper;

import com.poly.easylearning.dto.RoleDTO;
import com.poly.easylearning.entity.RoleApp;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RoleMapper implements Function<RoleApp, RoleDTO> {
	@Override
	public RoleDTO apply(RoleApp roleApp) {
		return RoleDTO.builder()
				.id(roleApp.getId())
				.role(roleApp.getName().getValue())
				.build();
	}
}