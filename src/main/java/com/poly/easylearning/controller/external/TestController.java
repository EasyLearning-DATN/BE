package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + "/test")
public class TestController {

	@GetMapping
	public String test(){
		return "OK";
	}
}
