package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.ReportStatusRQ;
import com.poly.easylearning.service.IReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_REPORT)
public class ReportAdminController {
	private final IReportService reportService;
	@GetMapping
	public ResponseEntity<?> getAllReport(
			@RequestParam(value = "type", defaultValue = "") String type,
			@RequestParam(value = "status", defaultValue = "") String status,
			@RequestParam(value = SystemConstant.CURRENT_PAGE, required = false) Optional<Integer> currentPage,
			@RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage
	){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(reportService.getAll(type, status, currentPage, limitPage));
	}

	@PatchMapping(SystemConstant.API_CHANGE_STATUS)
	public ResponseEntity<?> changeStatus(
			@RequestBody ReportStatusRQ statusRQ
			){
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(reportService.changeStatus(statusRQ));
	}
	@GetMapping(SystemConstant.PATH_ID)
	public ResponseEntity<?> findById(
			@PathVariable(name = SystemConstant.ID) UUID id) {
		return ResponseEntity
				.status(SystemConstant.STATUS_CODE_SUCCESS)
				.body(reportService.getDetail(id));
	}
}
