package com.poly.easylearning.controller.admin;


import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.RoleUpdateRQ;
import com.poly.easylearning.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_ROLE)
public class RoleController {
	private final IRoleService roleService;

	@PutMapping
	public ResponseEntity<?> updateRole(
			@RequestBody RoleUpdateRQ roleUpdate
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(roleService.updateRole(roleUpdate));
	}
	@GetMapping
	public ResponseEntity<?> getRoleByUser(
			@RequestParam(name = "userId") UUID userID
			){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(roleService.getAllRoleByUserId(userID));
	}
	@GetMapping("/all")
	public ResponseEntity<?> getAllRole(){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(roleService.getAll());
	}
}
