package com.poly.easylearning.service;

import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.ReportRQ;
import com.poly.easylearning.payload.request.ReportStatusRQ;
import com.poly.easylearning.payload.response.RestResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface IReportService {
	RestResponse createReport(MultipartFile imageRQ, ReportRQ reportRQ, User user);

	RestResponse changeStatus(ReportStatusRQ reportStatusRQ);

	RestResponse getAll(String type, String status, Optional<Integer> currentPage, Optional<Integer> limitPage);

	RestResponse getDetail(UUID id);
}
