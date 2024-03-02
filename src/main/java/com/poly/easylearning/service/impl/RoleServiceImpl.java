package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.dto.RoleDTO;
import com.poly.easylearning.entity.RoleApp;
import com.poly.easylearning.mapper.RoleMapper;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IRoleRepo;
import com.poly.easylearning.service.IRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class RoleServiceImpl implements IRoleService {
	private final IRoleRepo roleRepo;
	private final RoleMapper roleMapper;

	@Override
	public RestResponse getAllRoleByUserId(UUID userID) {
		List<RoleApp> roles = roleRepo.findAllByUserId(userID);
		List<RoleDTO> roleDTOS = roles.stream().map(role -> roleMapper.apply(role)).toList();
		return RestResponse.ok(ResourceBundleConstant.RL_6001, roleDTOS);
	}
}
