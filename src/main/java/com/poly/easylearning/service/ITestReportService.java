package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.TestReportRequest;
import com.poly.easylearning.payload.request.TestRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.TestReportResponse;
import com.poly.easylearning.payload.response.TestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ITestReportService {
    RestResponse<ListResponse<TestReportResponse>> getListTestReport(Integer userId, String dateStart, String dateEnd, String createdBy, PageRequest pageRequest);
    RestResponse<TestReportResponse> getOneTestReport(UUID id) throws DataNotFoundException;
    RestResponse<TestReportResponse> createTestReport(TestReportRequest testReportRequest);

    void deleteTestReport(UUID id) throws DataNotFoundException;
}
