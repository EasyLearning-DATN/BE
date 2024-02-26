package com.poly.easylearning.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserDTO {
	private String username;
	private String fullName;
	private String email;
	private String avatar;
	private LocalDate dayOfBirth;
}
