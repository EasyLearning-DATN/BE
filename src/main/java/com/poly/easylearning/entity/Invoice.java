package com.poly.easylearning.entity;

import com.poly.easylearning.enums.ReportStatus;
import com.poly.easylearning.enums.ReportType;
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
    private UUID id;
    private String orderID; // Mã hóa đơn (orderID)
    private String transId; // Mã yêu cầu (transId)
    private LocalDateTime date;
    private double total;
    private UUID userId;
    private String status;
}
