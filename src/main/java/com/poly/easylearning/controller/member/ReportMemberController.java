package com.poly.easylearning.controller.member;


import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.ReportStatusRQ;
import com.poly.easylearning.payload.request.ReportRQ;
import com.poly.easylearning.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_REPORT)
public class ReportMemberController {
	private final IReportService reportService;

	@PostMapping
	public ResponseEntity<?> createLessonReport(
			@AuthenticationPrincipal User user,
			@RequestBody ReportRQ reportRQ
	) {
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(reportService.createReport(reportRQ, user));
	}
}
