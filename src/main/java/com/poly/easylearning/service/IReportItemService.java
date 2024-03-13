package com.poly.easylearning.service;

import com.poly.easylearning.entity.ReportItem;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.response.RestResponse;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IReportItemService {
    List<ReportItem> createListReportItem(Set<UUID> questionTestIds, UUID testReportId, List<String> answerOfUser);
}
