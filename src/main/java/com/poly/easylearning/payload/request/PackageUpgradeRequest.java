package com.poly.easylearning.payload.request;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PackageUpgradeRequest {

    @NotBlank(message = "PKU_6005")
    @Size(min = 2, max = 255, message = "PKU_6006")
    private String name;

    private String description;

    @NotNull(message = "PKU_6009")
    @Min(value = 0, message = "PKU_6010")
    private double price;

    private Integer sale;

    @NotNull(message = "PKU_6011")
    private LocalDateTime expiry;
}
