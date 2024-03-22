package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ILessonRepo extends JpaRepository<Lesson, UUID> {
    @Query("SELECT l FROM Lesson l WHERE " +
            "((:keyword IS NULL OR :keyword = '' OR l.name LIKE %:keyword% OR l.description LIKE %:keyword%) " +
            "AND (:id IS NULL OR l.id = :id) " +
            "AND (((:dateStart IS NULL AND :dateEnd IS NULL) OR l.createdDate BETWEEN :dateStart AND :dateEnd) OR (:dateStart IS NULL AND l.createdDate <= :dateEnd) OR (:dateEnd IS NULL AND l.createdDate <= :dateStart)) " +
            "AND (:createdBy IS NULL OR l.createdBy = :createdBy) " +
            "AND (:isPublic IS NULL OR l.isPublic = :isPublic)) " +
            "AND (l.isDeleted = false )")
    Page<Lesson> searchLesson(String keyword, UUID id, LocalDateTime dateStart, LocalDateTime dateEnd, UUID createdBy, Boolean isPublic, Pageable pageable);

    @Query("SELECT l FROM Lesson l WHERE " +
            "(l.id = :id)" + " AND (l.isDeleted = false )")
    Optional<Lesson> getLessonById(UUID id);
}
