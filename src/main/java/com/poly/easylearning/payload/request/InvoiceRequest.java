package com.poly.easylearning.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.enums.InvoiceStatusEnum;
import com.poly.easylearning.enums.MethodPaymentEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class InvoiceRequest {
    @NotBlank(message = ResourceBundleConstant.INV_13006)
    @Size(min = 1, max = 255, message = "PKU_6006")
    @JsonProperty("order_id")
    private String orderID;

    @NotBlank(message = ResourceBundleConstant.INV_13007)
    @Size(min = 1, max = 255, message = "PKU_6006")
    @JsonProperty("trans_id")
    private String transId;

    @NotNull(message = ResourceBundleConstant.INV_13008)
    @Min(value = 0, message = ResourceBundleConstant.INV_13009)
    private double total;

    @NotNull(message = ResourceBundleConstant.INV_13011)
    private InvoiceStatusEnum status;

    @NotNull(message = ResourceBundleConstant.INV_13014)
    @JsonProperty("method_payment")
    private MethodPaymentEnum methodPayment;

    @NotNull(message = ResourceBundleConstant.INV_13012)
    @JsonProperty("user_info_id")
    private Integer userInfoId;

    @NotNull(message = ResourceBundleConstant.INV_13013)
    @JsonProperty("package_upgrade_id")
    private UUID packageUpgradeId;
}