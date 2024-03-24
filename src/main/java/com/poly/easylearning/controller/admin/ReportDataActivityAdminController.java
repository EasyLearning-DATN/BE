package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.response.ReportActivityResponse;
import com.poly.easylearning.payload.response.ReportFinanceResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IReportActivityService;
import com.poly.easylearning.service.IReportFinanceService;
import com.poly.easylearning.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_REPORT_ACTIVITY)
public class ReportDataActivityAdminController {
    private final IReportActivityService reportActivityService;

    @GetMapping
    public ResponseEntity<RestResponse<ReportActivityResponse>> getReportActivity(
            @RequestParam(value = SystemConstant.START_DATE, defaultValue = "") String startDate,
            @RequestParam(value = SystemConstant.END_DATE, defaultValue = "") String endDate
    ) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(reportActivityService.getReportDataActivity(DateUtil.parseLocalDate(startDate), DateUtil.parseLocalDate(endDate)));
    }
}
