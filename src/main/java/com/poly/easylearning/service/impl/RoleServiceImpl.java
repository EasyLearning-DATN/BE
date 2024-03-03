package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.dto.RoleDTO;
import com.poly.easylearning.entity.RoleApp;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.entity.UserRole;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.mapper.RoleMapper;
import com.poly.easylearning.payload.request.RoleUpdateRQ;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IRoleRepo;
import com.poly.easylearning.repo.IUserRoleRepo;
import com.poly.easylearning.service.IRoleService;
import com.poly.easylearning.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class RoleServiceImpl implements IRoleService {
	private final IRoleRepo roleRepo;
	private final IUserService userService;
	private final RoleMapper roleMapper;
	private final IUserRoleRepo userRoleRepo;
	@Override
	public RestResponse getAllRoleByUserId(UUID userID) {
		List<RoleApp> roles = roleRepo.findAllByUserId(userID);
		List<RoleDTO> roleDTOS = roles.stream().map(role -> roleMapper.apply(role)).toList();
		return RestResponse.ok(ResourceBundleConstant.RL_6001, roleDTOS);
	}

	@Override
	public RestResponse updateRole(RoleUpdateRQ roleUpdate) {
		User user = userService.findById(roleUpdate.getUserID());
		if(Objects.nonNull(user.getUserRoles())){
			userRoleRepo.deleteAllByUserId(user.getId());
		}
		if (Objects.nonNull(roleUpdate.getRoleIds()) && !roleUpdate.getRoleIds().isEmpty()) {
			roleUpdate.getRoleIds().forEach(roleID -> {
				Optional<RoleApp> roleOptional = roleRepo.findById(roleID);
				RoleApp role = roleOptional.orElseThrow(() -> new ApiRequestException(ResourceBundleConstant.LSN_4003));
				userRoleRepo.save(
						UserRole.builder()
								.role(role)
								.user(user)
								.build()
				);
			});
		}
		return RestResponse.ok(ResourceBundleConstant.RL_6002, "OK");
	}

	@Override
	public RestResponse getAll() {
		List<RoleApp> roles = roleRepo.findAll();
		List<RoleDTO> roleDTOS = roles.stream().map(role -> roleMapper.apply(role)).toList();
		return RestResponse.ok(ResourceBundleConstant.RL_6001, roleDTOS);
	}
}
