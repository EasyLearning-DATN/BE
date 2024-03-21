package com.poly.easylearning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poly.easylearning.enums.ReportStatus;
import com.poly.easylearning.enums.ReportType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ReportDTO {
	private UUID reportId;
	private LocalDateTime timeReport;
	private String reason;
	private ReportStatus status;
	private UUID targetId;
	private ReportType type;
	private String userReport;
	private String imageUrl;
}
