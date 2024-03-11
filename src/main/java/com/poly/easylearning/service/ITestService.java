package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.TestRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.TestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface ITestService {
    RestResponse<ListResponse<TestResponse>> getListTest(String keyword, String id, String dateStart, String dateEnd, String createdBy, PageRequest pageRequest);
    RestResponse<TestResponse> getOneTest(UUID id) throws DataNotFoundException;
    RestResponse<TestResponse> createTest(TestRequest testRequest);

    RestResponse<TestResponse> updateTest(UUID id, TestRequest testRequest) throws DataNotFoundException;

    void deleteTest(UUID id) throws DataNotFoundException;
}
