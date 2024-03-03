package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.UserStatusRQ;
import com.poly.easylearning.payload.request.UserUpdateRQ;
import com.poly.easylearning.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_USER)
public class UserAdminController {
	private final IUserService userService;

	@GetMapping
	public ResponseEntity<?> getAllUser(
			@RequestParam(value = SystemConstant.NAME, defaultValue = "") String name,
			@RequestParam(value = SystemConstant.CURRENT_PAGE, required = false) Optional<Integer> currentPage,
			@RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.findAllByCondition(name, currentPage, limitPage));
	}
	@GetMapping(SystemConstant.PATH_ID)
	public ResponseEntity<?> findById(
			@PathVariable(name = SystemConstant.ID) UUID id) {
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.getUserResById(id));
	}
	@PatchMapping(SystemConstant.API_CHANGE_STATUS + SystemConstant.PATH_ID)
	public ResponseEntity<?> changeUserStatus(
			@PathVariable(name = SystemConstant.ID) UUID userId,
			@RequestBody UserStatusRQ userStatusRQ
	) {
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.changeStatus(userId, userStatusRQ));
	}
	@DeleteMapping(SystemConstant.PATH_ID)
	public ResponseEntity<?> deleteUser(
			@PathVariable(name = SystemConstant.ID) UUID userId
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.deleteUser(userId));
	}
	@PatchMapping(SystemConstant.PATH_ID)
	public ResponseEntity<?> updateUserInfo(
			@PathVariable(name = SystemConstant.ID) UUID userId,
			@RequestBody UserUpdateRQ userUpdateRQ
			){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.updateUser(userUpdateRQ, userId));
	}
}
