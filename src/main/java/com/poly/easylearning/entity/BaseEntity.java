package com.poly.easylearning.entity;

import com.poly.easylearning.utils.SecurityContextUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private UUID createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private UUID lastModifiedBy;

    @Column(nullable = false)
    private Boolean isDeleted;

    @PrePersist
    protected void onCreate() {
        User user = SecurityContextUtils.getCurrentUser();
        isDeleted = false;
        createdBy = user != null ? user.getId() : null;
    }

    @PreUpdate
    protected void onUpdate() {
        User user = SecurityContextUtils.getCurrentUser();
        lastModifiedBy = user != null ? user.getId() : null ;
    }
}
