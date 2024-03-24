package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.entity.Invoice;
import com.poly.easylearning.enums.InvoiceStatusEnum;
import com.poly.easylearning.enums.MethodPaymentEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class InvoiceResponse extends BaseResponse{
    @JsonProperty("order_id")
    private String orderID;

    @JsonProperty("trans_id")
    private String transId;

    private double total;

    private LocalDateTime date;

    private InvoiceStatusEnum status;
    @JsonProperty("method_payment")
    private MethodPaymentEnum methodPayment;

    @JsonProperty("user_info_id")
    private Integer userInfoId;

    public static InvoiceResponse fromInvoice(Invoice invoice) {
        InvoiceResponse invoiceResponse = InvoiceResponse.builder()
                .id(invoice.getId())
                .createdDate(invoice.getCreatedDate())
                .createdBy(invoice.getCreatedBy())
                .lastModifiedDate(invoice.getLastModifiedDate())
                .lastModifiedBy(invoice.getLastModifiedBy())
                .orderID(invoice.getOrderID())
                .transId(invoice.getTransId())
                .total(invoice.getTotal())
                .date(invoice.getDate())
                .userInfoId(invoice.getUserInfo().getId())
                .status(invoice.getStatus())
                .methodPayment(invoice.getMethodPayment())
                .build();
        return invoiceResponse;
    }
}