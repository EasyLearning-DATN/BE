package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.TestReportRequest;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.TestReportResponse;
import com.poly.easylearning.service.ITestReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_TEST_REPORT)
public class TestReportMemberController {
    private final ITestReportService testReportService;

    @PostMapping("")
    public ResponseEntity<RestResponse<TestReportResponse>> createTestReport(
            @Valid @RequestBody TestReportRequest testReportRequest, BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(testReportService.createTestReport(testReportRequest));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteTestReport(@PathVariable(name = "id") UUID id) {
        testReportService.deleteTestReport(id);
        return ResponseEntity.noContent().build();
    }
}
