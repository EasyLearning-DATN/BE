package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.dto.InvoiceDTO;
import com.poly.easylearning.dto.RoleDTO;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.payload.request.InvoiceRQ;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IInvoiceRepo;
import com.poly.easylearning.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceRepo invoiceRepo;
//    get all invoice
    public RestResponse getAllInvoice(Optional<Integer> currentPage, Optional<Integer> limitPage) {
        List<Invoice> invoices = invoiceRepo.findAll();
        return RestResponse.ok(ResourceBundleConstant.INV_13003, invoices);
    }

//    get invoice by order id
    public RestResponse getInvoiceByOrderId(UUID id) {
        return null;
    }

    @Override
    public RestResponse createInvoice(InvoiceRQ invoiceRequest, User user) {
       Invoice invoice = invoiceRepo.save(
                    Invoice.builder()
                            .orderID(invoiceRequest.getOrderID())
                            .transId(invoiceRequest.getRequestId())
                            .date(LocalDateTime.now())
                            .total(invoiceRequest.getTotal())
                            .userId(invoiceRequest.getUserId())
                            .status(invoiceRequest.getStatus())
                            .build()
            );
            return RestResponse.ok(ResourceBundleConstant.INV_13002, invoice);
    }

    //    delete invoice
    public RestResponse deleteInvoice(UUID id) {
        return null;
    }

    @Override
    public RestResponse getInvoiceByUserId(UUID id) {
        return null;
    }


}
