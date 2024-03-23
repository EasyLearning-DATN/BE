package com.poly.easylearning.service;
import com.poly.easylearning.payload.response.*;

import java.time.LocalDate;

public interface IReportFinanceService {
    RestResponse<ReportFinanceResponse> getReportFinance(LocalDate startDate, LocalDate endDate);
}
