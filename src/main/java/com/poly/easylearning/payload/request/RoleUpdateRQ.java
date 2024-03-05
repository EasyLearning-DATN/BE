package com.poly.easylearning.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RoleUpdateRQ {
	private UUID userID;
	private List<UUID> roleIds;
}
