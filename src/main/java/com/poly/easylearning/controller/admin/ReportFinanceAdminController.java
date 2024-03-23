package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.enums.Scope;
import com.poly.easylearning.payload.request.CommentStatusRQ;
import com.poly.easylearning.payload.response.ReportFinanceResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.ICommentService;
import com.poly.easylearning.service.IReportFinanceService;
import com.poly.easylearning.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_REPORT_FINANCE)
public class ReportFinanceAdminController {
    private final IReportFinanceService reportFinanceService;

    @GetMapping
    public ResponseEntity<RestResponse<ReportFinanceResponse>> getReportFinance(
            @RequestParam(value = SystemConstant.START_DATE, defaultValue = "") String startDate,
            @RequestParam(value = SystemConstant.END_DATE, defaultValue = "") String endDate
    ) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(reportFinanceService.getReportFinance(DateUtil.parseLocalDate(startDate), DateUtil.parseLocalDate(endDate)));
    }
}
