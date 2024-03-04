package com.poly.easylearning.payload.request;

import com.poly.easylearning.enums.ReportStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ReportStatusRQ {
	private UUID reportId;
	private ReportStatus status;
}
