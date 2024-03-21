package com.poly.easylearning.service.impl;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.ViewResultType;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.ViewResultTypeRequest;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.ViewResultTypeResponse;
import com.poly.easylearning.repo.IViewResultTypeRepo;
import com.poly.easylearning.service.IViewResultTypeService;
import com.poly.easylearning.utils.ValidateUserUpdateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ViewResultTypeServiceImpl implements IViewResultTypeService {
    private final IViewResultTypeRepo viewResultTypeRepo;

    @Override
    public RestResponse<List<ViewResultTypeResponse>> getListViewResultType() {
        List<ViewResultType> pageReponse = viewResultTypeRepo.findAll();
        List<ViewResultTypeResponse> questionTypeResponses = pageReponse.stream().map(ViewResultTypeResponse::fromViewResultType).toList();
        return RestResponse.ok(ResourceBundleConstant.VRT_11003,
                questionTypeResponses);
    }

    @Override
    public RestResponse<ViewResultTypeResponse> getOneViewResultType(String code) throws DataNotFoundException {
        ViewResultType viewResultType = viewResultTypeRepo.findViewResultTypeByCode(code)
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.QST_5001));
        ViewResultTypeResponse viewResultTypeResponse = ViewResultTypeResponse.fromViewResultType(viewResultType);

        return RestResponse.ok(ResourceBundleConstant.VRT_11004, viewResultTypeResponse);
    }

    @Override
    public RestResponse<ViewResultTypeResponse> createViewResultType(ViewResultTypeRequest viewResultTypeRequest) {
        ViewResultType viewResultType = ViewResultType.builder()
                .name(viewResultTypeRequest.getName())
                .code(viewResultTypeRequest.getCode())
                .build();

        viewResultTypeRepo.save(viewResultType);
        ViewResultTypeResponse response = ViewResultTypeResponse.fromViewResultType(viewResultType);
        return RestResponse.created(ResourceBundleConstant.VRT_11002, response);
    }

    @Override
    public RestResponse<ViewResultTypeResponse> updateViewResultType(UUID id, ViewResultTypeRequest viewResultTypeRequest) throws DataNotFoundException {
        ViewResultType existingViewResultType =
                viewResultTypeRepo.findById(id)
                        .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.VRT_11001));
        ValidateUserUpdateUtils.checkUserUpdate(existingViewResultType);
        existingViewResultType.setName(viewResultTypeRequest.getName());
        existingViewResultType.setCode(viewResultTypeRequest.getCode());

        ViewResultType viewResultType = viewResultTypeRepo.save(existingViewResultType);

        ViewResultTypeResponse response = ViewResultTypeResponse.fromViewResultType(viewResultType);
        return RestResponse.accepted(ResourceBundleConstant.VRT_11008, response);
    }

    @Override
    public void deleteViewResultType(UUID id) throws DataNotFoundException {
        viewResultTypeRepo.deleteById(id);
    }
}
