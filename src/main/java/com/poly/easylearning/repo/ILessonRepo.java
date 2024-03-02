package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ILessonRepo extends JpaRepository<Lesson, UUID> {
    @Query("SELECT l FROM Lesson l WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR l.name LIKE %:keyword% OR l.description LIKE %:keyword%)" + " AND (l.isDeleted = false )")
    Page<Lesson> searchLesson(String keyword, Pageable pageable);

    @Query("SELECT l FROM Lesson l WHERE " +
            "(l.id = :id)" + " AND (l.isDeleted = false )")
    Optional<Lesson> getLessonById(UUID id);

    Page<Lesson> findLessonsByCreatedBy(String username, Pageable pageable);
}
