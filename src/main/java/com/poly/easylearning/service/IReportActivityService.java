package com.poly.easylearning.service;

import com.poly.easylearning.payload.response.ReportActivityResponse;
import com.poly.easylearning.payload.response.ReportFinanceResponse;
import com.poly.easylearning.payload.response.RestResponse;

import java.time.LocalDate;

public interface IReportActivityService {
    RestResponse<ReportActivityResponse> getReportDataActivity(LocalDate startDate, LocalDate endDate);
}
