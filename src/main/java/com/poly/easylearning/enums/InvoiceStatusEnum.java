package com.poly.easylearning.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InvoiceStatusEnum {
    UNPAID("unpaid"), PAID("paid"), PENDING("pending"),
    CANCELLED("cancelled"), REFUNDED("refunded"), PROCESSING("processing");
    private String value;

    InvoiceStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
