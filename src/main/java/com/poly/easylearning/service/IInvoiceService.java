package com.poly.easylearning.service;

import com.poly.easylearning.payload.request.*;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.entity.User;

import java.util.Optional;
import java.util.UUID;
public interface IInvoiceService {
    RestResponse getAllInvoice(Optional<Integer> currentPage, Optional<Integer> limitPage);

    RestResponse getInvoiceByOrderId(UUID id);

    RestResponse createInvoice(InvoiceRQ invoiceRequest, User user);
    boolean existsByOrderId(String orderId);

    RestResponse deleteInvoice(UUID id);

    RestResponse getInvoiceByUserId(UUID id);





}
