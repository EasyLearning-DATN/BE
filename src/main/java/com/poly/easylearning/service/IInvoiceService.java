package com.poly.easylearning.service;

import com.poly.easylearning.payload.request.*;
import com.poly.easylearning.payload.response.InvoiceResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.entity.User;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;
public interface IInvoiceService {
    RestResponse<ListResponse<InvoiceResponse>> getListInvoice(String status, Integer userInfoId, String dateStart, String dateEnd, String orderId, String transId, Pageable pageable);
    RestResponse<InvoiceResponse> getOneInvoice(UUID id);

    RestResponse<InvoiceResponse> createInvoice(InvoiceRequest invoiceRequest);
}
