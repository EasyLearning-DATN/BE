package com.poly.easylearning.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MethodPaymentEnum {
    MOMO("momo"), VN_PAY("vn_pay");
    private String value;

    MethodPaymentEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
