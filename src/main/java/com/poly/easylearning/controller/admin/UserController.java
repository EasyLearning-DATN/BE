package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_USER)
public class UserController {

	@GetMapping
	public ResponseEntity<?> getAllUser(){
		//do st
		return ResponseEntity.status(HttpStatus.OK).body("st");
	}
	@GetMapping(SystemConstant.PATH_ID)
	public ResponseEntity<?> findById(
			@PathVariable(name = SystemConstant.ID) UUID id) {
		return ResponseEntity.status(HttpStatus.OK).body(
				null
		);
	}
}
