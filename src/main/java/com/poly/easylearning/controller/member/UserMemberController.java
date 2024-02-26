package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.UserUpdateRQ;
import com.poly.easylearning.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_USER)
public class UserMemberController {
	private final IUserService userService;
	@GetMapping(SystemConstant.API_GET_USERINFO)
	public ResponseEntity<?> getInfo(@RequestParam(name = "token") String token){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.getInfo(token));
	}
	@PostMapping(SystemConstant.API_UPDATE_USERINFO)
	public ResponseEntity<?> updateInfo(@RequestBody UserUpdateRQ userUpdateRQ
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.updateInfo(userUpdateRQ));
	}
	@PatchMapping(SystemConstant.API_UPDATE_AVATAR)
	public ResponseEntity<?> updateAvatar(
			@RequestParam(name = "id") UUID userID,
			@RequestParam(name = "avatar") MultipartFile avatarFile
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.updateAvatar(userID, avatarFile));
	}
	@DeleteMapping
	public ResponseEntity<?> lockAccount(@RequestParam(name = "id") UUID userID) {
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.lockAccount(userID));
	}
}
