package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.*;
import com.poly.easylearning.enums.InvoiceStatusEnum;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.exception.InvalidUserException;
import com.poly.easylearning.payload.request.InvoiceRequest;
import com.poly.easylearning.payload.response.InvoiceResponse;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IInvoiceRepo;
import com.poly.easylearning.repo.IPackageUpgradeRepo;
import com.poly.easylearning.service.IInvoiceService;
import com.poly.easylearning.utils.DateUtil;
import com.poly.easylearning.utils.SecurityContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceRepo invoiceRepo;
    private final IPackageUpgradeRepo packageUpgradeRepo;

    public RestResponse<ListResponse<InvoiceResponse>> getListInvoice(String status, Integer userInfoId, String dateStart, String dateEnd, String orderId, String transId, Pageable pageable) {
        InvoiceStatusEnum invoiceStatus = null;
        if(!status.isEmpty()){
            try {
                invoiceStatus = InvoiceStatusEnum.valueOf(status);
            } catch (Exception e) {
                throw new InvalidParameterException(ResourceBundleConstant.SYS_1002);
            }
        }

        Page<Invoice> pageReponse = invoiceRepo.searchInvoice(invoiceStatus, userInfoId, DateUtil.fromString(dateStart), DateUtil.fromString(dateEnd), orderId, transId, pageable);
        List<InvoiceResponse> invoiceResponses = pageReponse.get().map(InvoiceResponse::fromInvoice).toList();
        ListResponse<InvoiceResponse> listResponse = ListResponse.build(pageReponse.getTotalPages(), invoiceResponses);
        return RestResponse.ok(ResourceBundleConstant.INV_13003,
                listResponse);
    }

    @Override
    public RestResponse<InvoiceResponse> getOneInvoice(UUID id) throws DataNotFoundException {
        Invoice invoice = invoiceRepo.getInvoiceById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.INV_13001));
        InvoiceResponse invoiceResponse = InvoiceResponse.fromInvoice(invoice);

        return RestResponse.ok(ResourceBundleConstant.INV_13004, invoiceResponse);
    }

    @Override
    public RestResponse<InvoiceResponse> createInvoice(InvoiceRequest invoiceRequest) {
        User user = SecurityContextUtils.getCurrentUser();
        UserInfo userInfo;
        if(user != null){
            userInfo = user.getUserInfo();
        }else {
            throw new InvalidUserException(ResourceBundleConstant.USR_2020);
        }

        PackageUpgrade packageUpgrade = packageUpgradeRepo.getPackageUpgradeById(invoiceRequest.getPackageUpgradeId())
                .orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.PKU_6001));

        Invoice invoice = invoiceRepo.save(
                Invoice.builder()
                        .orderID(invoiceRequest.getOrderID())
                        .transId(invoiceRequest.getTransId())
                        .date(LocalDateTime.now())
                        .total(invoiceRequest.getTotal())
                        .userInfo(userInfo)
                        .status(invoiceRequest.getStatus())
                        .methodPayment(invoiceRequest.getMethodPayment())
                        .packageUpgrade(packageUpgrade)
                        .build()
        );
        return RestResponse.ok(ResourceBundleConstant.INV_13002, InvoiceResponse.fromInvoice(invoice));
    }


}
