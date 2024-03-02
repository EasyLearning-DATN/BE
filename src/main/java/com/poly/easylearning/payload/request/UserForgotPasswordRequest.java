package com.poly.easylearning.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserForgotPasswordRequest {
	private String email;
}
