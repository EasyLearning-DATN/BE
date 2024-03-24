package com.poly.easylearning.utils;

import com.poly.easylearning.constant.ResourceBundleConstant;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {
    public static LocalDateTime fromString(String localDateTime) {
        try {
            if(localDateTime.trim().isEmpty()){
                return null;
            }
            return LocalDateTime.parse(localDateTime);
        } catch (Exception e) {
            throw new InvalidParameterException(ResourceBundleConstant.DAT_8001);
        }
    }

    public static LocalDate parseLocalDate(String localDate) {
        try {
            if(localDate.trim().isEmpty()){
                return null;
            }
            return LocalDate.parse(localDate);
        } catch (Exception e) {
            throw new InvalidParameterException(ResourceBundleConstant.DAT_8001);
        }
    }
}
