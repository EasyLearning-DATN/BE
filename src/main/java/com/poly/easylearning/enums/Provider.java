package com.poly.easylearning.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Provider {
    LOCAL("LOCAL"), GOOGLE("GOOGLE"), SOCIAL("SOCIAL");
    private String value;
    Provider(String value){
        this.value = value;
    }
    @JsonValue
    public String getValue(){
        return this.value;
    }
}
