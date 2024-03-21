package com.poly.easylearning.entity;

import com.poly.easylearning.enums.InvoiceStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`invoice`")
@Entity
public class Invoice extends BaseEntity{
    @JoinColumn(name = "order_id", unique = true)
    private String orderID; // Mã hóa đơn (orderID)
    @JoinColumn(name = "trans_id", unique = true)
    private String transId; // Mã yêu cầu (transId)
    private LocalDateTime date;
    private double total;
    @Enumerated(EnumType.STRING)
    private InvoiceStatusEnum status;
    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;
    @ManyToOne
    @JoinColumn(name = "package_upgrade_id")
    private PackageUpgrade packageUpgrade;
}
