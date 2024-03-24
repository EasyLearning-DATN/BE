package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.payload.response.ReportFinanceResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IInvoiceRepo;
import com.poly.easylearning.service.IReportFinanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ReportFinanceServiceImpl implements IReportFinanceService {
    private final IInvoiceRepo invoiceRepo;
    private static final String REVENUE_QUARTER = "revenueQuarter";
    private static final String REVENUE_TOTAL = "revenueTotal";
    @Override
    public RestResponse<ReportFinanceResponse> getReportFinance(LocalDate startDate, LocalDate endDate) {
        Map<String, Double> revenue = invoiceRepo.getRevenue(startDate, endDate);
        ReportFinanceResponse response = ReportFinanceResponse.builder()
                .revenueQuarter(revenue.get(REVENUE_QUARTER))
                .revenueTotal(revenue.get(REVENUE_TOTAL))
                .build();
         return RestResponse.ok(ResourceBundleConstant.RPF_14003, response);
    }
}
