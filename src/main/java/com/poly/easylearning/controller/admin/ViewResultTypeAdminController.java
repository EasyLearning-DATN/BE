package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.QuestionTypeRequest;
import com.poly.easylearning.payload.request.ViewResultTypeRequest;
import com.poly.easylearning.payload.response.QuestionTypeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.ViewResultTypeResponse;
import com.poly.easylearning.service.IQuestionTypeService;
import com.poly.easylearning.service.IViewResultTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_VIEW_RESULT_TYPE)
public class ViewResultTypeAdminController {
    private final IViewResultTypeService viewResultTypeService;

    @PostMapping("")
    public ResponseEntity<RestResponse<ViewResultTypeResponse>> createViewResultType(
            @Valid @RequestBody ViewResultTypeRequest viewResultTypeRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(viewResultTypeService.createViewResultType(viewResultTypeRequest));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<ViewResultTypeResponse>> updateViewResultType(@PathVariable(name = "id") UUID id,
                                                                     @Valid @RequestBody ViewResultTypeRequest viewResultTypeRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(viewResultTypeService.updateViewResultType(id, viewResultTypeRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteViewResultType(@PathVariable(name = "id") UUID id) {
        viewResultTypeService.deleteViewResultType(id);
        return ResponseEntity.noContent().build();
    }
}
