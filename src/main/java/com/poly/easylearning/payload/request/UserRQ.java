package com.poly.easylearning.payload.request;


import com.poly.easylearning.enums.Provider;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserRQ {
	private String username;
	private String password;
	private String fullName;
	private String email;
	private LocalDate dayOfBirth;
	private Provider provider;
}
