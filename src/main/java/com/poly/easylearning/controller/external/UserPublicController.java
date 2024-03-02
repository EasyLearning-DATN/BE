package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.AuthRequest;
import com.poly.easylearning.payload.request.UserForgotPasswordRequest;
import com.poly.easylearning.payload.request.UserRQ;
import com.poly.easylearning.service.AuthService;
import com.poly.easylearning.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_USER)
public class UserPublicController {
	private final IUserService userService;
	private final AuthService authService;

	@PostMapping(SystemConstant.API_SIGNUP)
	public ResponseEntity<?> doRegister(@RequestBody UserRQ userRequest){
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
	@PostMapping(SystemConstant.API_FORGOT_PASSWORD)
	public ResponseEntity<?> forgotPassword(
			@Valid @RequestBody UserForgotPasswordRequest request
	) {
		return new ResponseEntity<>(userService.forgotPassword(request), HttpStatus.OK);
	}
}
