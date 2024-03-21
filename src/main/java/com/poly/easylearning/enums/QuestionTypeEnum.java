package com.poly.easylearning.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionTypeEnum {
    SINGEL_CORRECT_ANSWER("sca"), MULTILPLE_CORRECT_ANSWER("mcs"), FILL_IN_THE_BLANK("fitb");
    private String value;

    QuestionTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
