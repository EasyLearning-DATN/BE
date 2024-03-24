package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.TestReportRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.TestReportResponse;
import com.poly.easylearning.service.ITestReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping("")
    public ResponseEntity<RestResponse<ListResponse<TestReportResponse>>> getListTestReport(
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "dateStart", defaultValue = "") String dateStart,
            @RequestParam(value = "dateEnd", defaultValue = "") String dateEnd,
            @RequestParam(value = "createdBy", defaultValue = "") String createdBy
    ) {

        //Format sắp xếp
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(page, limit, direction, sortBy);
        return ResponseEntity.ok(testReportService.getListTestReport(userId, dateStart, dateEnd, createdBy, pageRequest));
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<TestReportResponse>> getOneTestReport(
            @PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(testReportService.getOneTestReport(id));
    }

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
