package com.poly.easylearning.controller.admin;


import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_ROLE)
public class RoleController {
	private final IRoleService roleService;

//	@GetMapping
//	public ResponseEntity<?> updateRole(){
//		return ResponseEntity
//				.status(SystemConstant.STATUS_CODE_SUCCESS)
//				.body(roleService.updateRole());
//	}
	@GetMapping
	public ResponseEntity<?> getRoleByUser(
			@RequestParam(name = "userId") UUID userID
			){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(roleService.getAllRoleByUserId(userID));
	}
}
