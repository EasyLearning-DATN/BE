package com.poly.easylearning.mapper;

import com.poly.easylearning.dto.ReportDTO;
import com.poly.easylearning.entity.Report;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReportMapper implements Function<Report, ReportDTO> {
	@Override
	public ReportDTO apply(Report report) {
		return ReportDTO.builder()
				.reportId(report.getId())
				.targetId(report.getTargetId())
				.timeReport(report.getTimeReport())
				.reason(report.getReason())
				.status(report.getStatus())
				.type(report.getType())
				.userReport(report.getUser().getUsername())
				.imageUrl(report.getImage() != null ? report.getImage().getUrl() : "")
				.build();
	}
}
