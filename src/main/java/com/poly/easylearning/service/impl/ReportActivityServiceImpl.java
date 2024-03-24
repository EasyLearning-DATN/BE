package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.payload.response.ReportActivityResponse;
import com.poly.easylearning.payload.response.ReportFinanceResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IInvoiceRepo;
import com.poly.easylearning.repo.ILessonRepo;
import com.poly.easylearning.repo.ITestReportRepo;
import com.poly.easylearning.repo.IUserRepo;
import com.poly.easylearning.service.IReportActivityService;
import com.poly.easylearning.service.IReportFinanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class ReportActivityServiceImpl implements IReportActivityService {
    private final IInvoiceRepo invoiceRepo;
    private final IUserRepo userRepo;
    private final ITestReportRepo testReportRepo;
    private final ILessonRepo lessonRepo;
    private static final String SALE_TOTAL = "saleTotal";
    private static final String SALE_QUARTER = "saleQuarter";
    private static final String USER_AMOUNT_QUARTER = "userAmountQuarter";
    private static final String DOING_TEST_AMOUNT_QUARTER = "doingTestAmountQuarter";
    private static final String CREATE_LESSON_TIME_QUARTER = "createLessonTimeQuarter";

    @Override
    public RestResponse<ReportActivityResponse> getReportDataActivity(LocalDate startDate, LocalDate endDate) {
        Map<String, Long> sale = invoiceRepo.getDataSaleAmount(startDate, endDate);
        Map<String, Long> userNewAmount = userRepo.getUserAmount(startDate, endDate);
        Map<String, Long> doingTestAmount = testReportRepo.getDoingTestAmount(startDate, endDate);
        Map<String, Long> createLessonAmount = lessonRepo.getCreateLessonTime(startDate, endDate);
        ReportActivityResponse response = ReportActivityResponse.builder()
                .saleTotal(sale.get(SALE_TOTAL))
                .saleQuarter(sale.get(SALE_QUARTER))
                .userAmoutQuarter(userNewAmount.get(USER_AMOUNT_QUARTER))
                .doingTestAmountQuarter(doingTestAmount.get(DOING_TEST_AMOUNT_QUARTER))
                .createLessonAmountQuarter(createLessonAmount.get(CREATE_LESSON_TIME_QUARTER))
                .build();
        return RestResponse.ok(ResourceBundleConstant.RDA_15003, response);
    }
}
