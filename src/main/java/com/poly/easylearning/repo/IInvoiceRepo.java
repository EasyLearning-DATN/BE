package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInvoiceRepo extends JpaRepository<Invoice, UUID> {
    @Query("""
            SELECT i FROM Invoice i
            WHERE i.orderID = :orderID
            AND i.isDeleted != TRUE
            """)
    Optional<Invoice> findByOrderID(String orderID);

    @Query("""
            SELECT i FROM Invoice i
            WHERE i.isDeleted != TRUE
            """)
    Page<Invoice> findAll(Pageable pageable);

    @Query("""
            SELECT i FROM Invoice i
            WHERE i.isDeleted != TRUE
            AND i.orderID = :orderID
            """)
    Optional<Invoice> findByOrderID(UUID orderID);

    @Query("""
            SELECT i FROM Invoice i
            WHERE i.isDeleted != TRUE
            AND i.id = :id
            """)
    Optional<Invoice> findById(UUID id);

//    existsByOrderId
    @Query("""
            SELECT CASE WHEN COUNT(i) > 0 THEN TRUE ELSE FALSE END
            FROM Invoice i
            WHERE i.orderID = :orderId
            AND i.isDeleted != TRUE
            """)
    boolean existsByOrderId(String orderId);

    @Query("""
            SELECT i FROM Invoice i
            WHERE i.isDeleted != TRUE
            AND i.userId = :userId
            """)
    List<Invoice> findByUserId(UUID userId);


}
