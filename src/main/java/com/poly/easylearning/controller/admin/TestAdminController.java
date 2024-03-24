package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.TestRequest;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.TestResponse;
import com.poly.easylearning.service.ITestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_TEST)
public class TestAdminController {
    private final ITestService testService;

    @PostMapping("")
    public ResponseEntity<RestResponse<TestResponse>> createTest(
            @Valid @RequestBody TestRequest testRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(testService.createTest(testRequest));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<TestResponse>> updateTest(@PathVariable(name = "id") UUID id,
                                                                     @Valid @RequestBody TestRequest testRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(testService.updateTest(id, testRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteTest(@PathVariable(name = "id") UUID id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
