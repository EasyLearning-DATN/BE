package com.poly.easylearning.payload.request;

import com.poly.easylearning.enums.Provider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserUpdateRQ {
	private UUID userID;
	private String username;
	private String fullName;
	private String email;
	private LocalDate dayOfBirth;
}
