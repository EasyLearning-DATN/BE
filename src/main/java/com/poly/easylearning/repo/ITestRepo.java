package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ITestRepo extends JpaRepository<Test, UUID> {
    @Query("SELECT t FROM Test t WHERE " +
            "((:keyword IS NULL OR :keyword = '' OR t.name LIKE %:keyword% OR t.description LIKE %:keyword%) " +
            "AND (:id IS NULL OR t.id = :id) " +
            "AND (((:dateStart IS NULL AND :dateEnd IS NULL) OR t.createdDate BETWEEN :dateStart AND :dateEnd) OR (:dateStart IS NULL AND t.createdDate <= :dateEnd) OR (:dateEnd IS NULL AND t.createdDate <= :dateStart)) " +
            "AND (:createdBy IS NULL OR t.createdBy = :createdBy)) " +
            "AND (t.isDeleted = false )")
    Page<Test> searchTest(String keyword, UUID id, LocalDateTime dateStart, LocalDateTime dateEnd, UUID createdBy, Pageable pageable);

    @Query("SELECT t FROM Test t WHERE " +
            "(t.id = :id)" + " AND (t.isDeleted = false )")
    Optional<Test> getTestById(UUID id);
}
