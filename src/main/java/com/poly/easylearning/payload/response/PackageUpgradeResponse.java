package com.poly.easylearning.payload.response;

import com.poly.easylearning.entity.PackageUpgrade;
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
public class PackageUpgradeResponse extends BaseResponse{

    private String name;

    private String description;

    private double price;

    private Integer sale;

    private Integer expiry;

    public static PackageUpgradeResponse fromPackageUpgrade(PackageUpgrade packageUpgrade) {
        PackageUpgradeResponse packageUpgradeResponse = PackageUpgradeResponse.builder()
                .id(packageUpgrade.getId())
                .createdDate(packageUpgrade.getCreatedDate())
                .createdBy(packageUpgrade.getCreatedBy())
                .lastModifiedDate(packageUpgrade.getLastModifiedDate())
                .lastModifiedBy(packageUpgrade.getLastModifiedBy())
                .name(packageUpgrade.getName())
                .description(packageUpgrade.getDescription())
                .price(packageUpgrade.getPrice())
                .sale(packageUpgrade.getSale())
                .expiry(packageUpgrade.getExpiry())
                .build();
        return packageUpgradeResponse;
    }
}