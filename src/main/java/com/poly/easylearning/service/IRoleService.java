package com.poly.easylearning.service;

import com.poly.easylearning.payload.request.RoleUpdateRQ;
import com.poly.easylearning.payload.response.RestResponse;

import java.util.UUID;

public interface IRoleService {
	RestResponse getAllRoleByUserId(UUID userID);

	RestResponse updateRole(RoleUpdateRQ roleUpdate);

	RestResponse getAll();
}
