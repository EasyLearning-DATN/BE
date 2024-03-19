package com.poly.easylearning.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.poly.easylearning.enums.Provider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class InvoiceDTO {
    private UUID id;
    private String orderID; // Mã hóa đơn (orderID)
    private String transId; // Mã yêu cầu (transId)
    private LocalDate date;
    private double total;
    private UUID userId;
    private String status;

}
