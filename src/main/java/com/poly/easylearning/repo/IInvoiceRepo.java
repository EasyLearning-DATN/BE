package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Invoice;
import com.poly.easylearning.enums.InvoiceStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IInvoiceRepo extends JpaRepository<Invoice, UUID> {
    @Query("SELECT i FROM Invoice i WHERE " +
            "((:status IS NULL OR :status = '' OR i.status = :status) " +
            "AND (:userInfoId IS NULL OR i.userInfo.id = :userInfoId) " +
            "AND (((:dateStart IS NULL AND :dateEnd IS NULL) OR i.date BETWEEN :dateStart AND :dateEnd) OR (:dateStart IS NULL AND i.date <= :dateEnd) OR (:dateEnd IS NULL AND i.createdDate <= :dateStart)) " +
            "AND (:orderId IS NULL OR :orderId = '' OR i.orderID LIKE :orderId) " +
            "AND (:transId IS NULL OR :transId = '' OR i.transId LIKE :transId)) " +
            "AND (i.isDeleted = false )")
    Page<Invoice> searchInvoice(InvoiceStatusEnum status, Integer userInfoId, LocalDateTime dateStart, LocalDateTime dateEnd, String orderId, String transId, Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE " +
            "(i.id = :id)" + " AND (i.isDeleted = false )")
    Optional<Invoice> getInvoiceById(UUID id);

}
