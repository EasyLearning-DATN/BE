package com.poly.easylearning.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleName {
    ROLE_USER("user"),ROLE_ADMIN("admin"),ROLE_PREMIUM("premium");

    private String role;
    RoleName(String role){
        this.role = role;
    }

    @JsonValue
    public String getValue(){
        return this.role;
    }

}
