package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.ViewResultTypeRequest;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.ViewResultTypeResponse;

import java.util.UUID;
import java.util.List;

public interface IViewResultTypeService {
    RestResponse<List<ViewResultTypeResponse>> getListViewResultType();

    RestResponse<ViewResultTypeResponse> getOneViewResultType(String code) throws DataNotFoundException;

    RestResponse<ViewResultTypeResponse> createViewResultType(ViewResultTypeRequest viewResultTypeRequest);

    RestResponse<ViewResultTypeResponse> updateViewResultType(UUID id, ViewResultTypeRequest viewResultTypeRequest) throws DataNotFoundException;

    void deleteViewResultType(UUID id) throws DataNotFoundException;
}
