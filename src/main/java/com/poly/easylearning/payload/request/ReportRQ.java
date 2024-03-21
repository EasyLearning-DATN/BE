package com.poly.easylearning.payload.request;

import com.poly.easylearning.enums.ReportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRQ {
	private UUID targetId;
	private String reason;
	private ReportType type;
	private MultipartFile image;
}
