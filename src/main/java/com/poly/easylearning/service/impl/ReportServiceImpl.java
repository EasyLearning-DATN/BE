package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.constant.UploadFolder;
import com.poly.easylearning.dto.ReportDTO;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.entity.Report;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.ReportStatus;
import com.poly.easylearning.enums.ReportType;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.mapper.ReportMapper;
import com.poly.easylearning.payload.request.ReportRQ;
import com.poly.easylearning.payload.request.ReportStatusRQ;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IReportRepo;
import com.poly.easylearning.service.IImageStorageService;
import com.poly.easylearning.service.IReportService;
import com.poly.easylearning.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportServiceImpl implements IReportService {
	private final IReportRepo reportRepo;
	private final ReportMapper reportMapper;
	private final IImageStorageService storageService;

	@Override
	public RestResponse createReport(MultipartFile imageRQ, ReportRQ reportRQ, User user) {
		Image image = null;
		if(Objects.nonNull(imageRQ)){
			image = storageService.upload(imageRQ, UploadFolder.REPORT);
		}
		Report reportSaved = reportRepo.save(
				Report.builder()
						.timeReport(LocalDateTime.now())
						.reason(reportRQ.getReason())
						.status(ReportStatus.PENDING)
						.targetId(reportRQ.getTargetId())
						.type(reportRQ.getType())
						.user(user)
						.image(image)
						.isDeleted(false)
						.build()
		);
		return RestResponse.ok(ResourceBundleConstant.RP_7001, reportMapper.apply(reportSaved));
	}

	@Override
	public RestResponse changeStatus(ReportStatusRQ reportStatusRQ) {
		Report reportCheck = reportRepo.findById(reportStatusRQ.getReportId())
				.orElseThrow(() -> new ApiRequestException(ResourceBundleConstant.RP_7004));
		reportCheck.setStatus(reportStatusRQ.getStatus());
		Report reportSaved = reportRepo.save(reportCheck);
		return RestResponse.ok(ResourceBundleConstant.RP_7003, reportMapper.apply(reportSaved));
	}

	@Override
	public RestResponse getAll(String type, String status, Optional<Integer> currentPage, Optional<Integer> limitPage) {
		Pageable pageable = ResponseUtil.pageable(currentPage.orElse(1),
				limitPage.orElse(DefaultValueConstants.LIMIT_PAGE));
		Page<Report> reports = null;
		if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(status)){
			ReportType typeEnum = ReportType.valueOf(type.toUpperCase());
			ReportStatus statusEnum = ReportStatus.valueOf(status.toUpperCase());
			reports = reportRepo.findAllByCondition(typeEnum, statusEnum, pageable);
			List<ReportDTO> reportDTOS = reports.stream().map(report -> reportMapper.apply(report)).toList();
			ListResponse<ReportDTO> listResponse = ListResponse.build(reports.getTotalPages(), reportDTOS);

			return RestResponse.ok(ResourceBundleConstant.RP_7002, listResponse);
		}
		if(StringUtils.isNotEmpty(status)){
			ReportStatus statusEnum = ReportStatus.valueOf(status.toUpperCase());
			reports = reportRepo.findByStatus(statusEnum, pageable);
		}else if(StringUtils.isNotEmpty(type)){
			ReportType typeEnum = ReportType.valueOf(type.toUpperCase());
			reports = reportRepo.findByType(typeEnum, pageable);
		}else{
			reports = reportRepo.findAll(pageable);
		}
		List<ReportDTO> reportDTOS = reports.stream().map(report -> reportMapper.apply(report)).toList();
		ListResponse<ReportDTO> listResponse = ListResponse.build(reports.getTotalPages(), reportDTOS);

		return RestResponse.ok(ResourceBundleConstant.RP_7002, listResponse);
	}

	@Override
	public RestResponse getDetail(UUID id) {
		Report reportFound = reportRepo.findById(id)
				.orElseThrow(() -> new ApiRequestException(ResourceBundleConstant.RP_7004));
		return RestResponse.ok(ResourceBundleConstant.RP_7002, reportMapper.apply(reportFound));
	}
}
