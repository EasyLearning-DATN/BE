package com.poly.easylearning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name="package_upgrade")
@Entity
public class PackageUpgrade extends BaseEntity{
    @Column(length = 255, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    private Integer sale;

    @Column(nullable = false)
    private Integer expiry;

    @OneToMany(mappedBy = "packageUpgrade")
    private List<Invoice> invoices;
}