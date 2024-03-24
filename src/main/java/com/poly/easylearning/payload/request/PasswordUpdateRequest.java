package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateRequest {
	@JsonProperty("password_old")
	private String passwordOld;
	@JsonProperty("password_new")
	private String passwordNew;
}
