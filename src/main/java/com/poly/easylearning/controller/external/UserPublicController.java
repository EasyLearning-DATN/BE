package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.AuthRequest;
import com.poly.easylearning.payload.request.PasswordUpdateRequest;
import com.poly.easylearning.payload.request.UserForgotPasswordRequest;
import com.poly.easylearning.payload.request.UserRQ;
import com.poly.easylearning.service.AuthService;
import com.poly.easylearning.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_USER)
public class UserPublicController {
	private final IUserService userService;
	private final AuthService authService;

	@PostMapping(SystemConstant.API_SIGNUP)
	public ResponseEntity<?> doRegister(
			 UserRQ userRequest
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(userService.register(userRequest));
	}
	@PostMapping(SystemConstant.API_AUTHENTICATION)
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(authService.authenticate(authRequest));
	}

	//generate token forgot password
	@GetMapping(SystemConstant.API_GET_TOKEN_FORGOT_PASSWORD)
	public ResponseEntity<?> generateCodeForgotPassword(@RequestParam(name = "email") String email) {
		return new ResponseEntity<>(userService.generateTokenForgotPass(email), HttpStatus.OK);
	}
	@GetMapping(SystemConstant.API_VALID_TOKEN)
	public ResponseEntity<?> validTokenForgotPass(
			@RequestParam(name = "token") String token
	) {
		return new ResponseEntity<>(userService.validTokenForgotPass(token), HttpStatus.OK);
	}
}
