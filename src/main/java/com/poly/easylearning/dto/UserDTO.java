package com.poly.easylearning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserDTO {
	private UUID id;
	private String username;
	private String fullName;
	private String email;
	private String avatar;
	private LocalDate dayOfBirth;
}
