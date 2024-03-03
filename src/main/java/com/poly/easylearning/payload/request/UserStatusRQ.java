package com.poly.easylearning.payload.request;

import com.poly.easylearning.enums.UserStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusRQ {
	private UserStatus status;
}
