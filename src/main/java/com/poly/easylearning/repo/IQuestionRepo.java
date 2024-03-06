package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IQuestionRepo extends JpaRepository<Question, UUID> {
    @Query("SELECT q FROM Question q WHERE " +
            "((:keyword IS NULL OR :keyword = '' OR q.title LIKE %:keyword% OR q.description LIKE %:keyword%) " +
            "AND (:id IS NULL OR q.id = :id) " +
            "AND (((:dateStart IS NULL AND :dateEnd IS NULL) OR q.createdDate BETWEEN :dateStart AND :dateEnd) OR (:dateStart IS NULL AND q.createdDate <= :dateEnd) OR (:dateEnd IS NULL AND q.createdDate <= :dateStart)) " +
            "AND (:createdBy IS NULL OR q.createdBy = :createdBy) " +
            "AND (:lessonId IS NULL OR q.lesson.id = :lessonId)) " +
            "AND (q.isDeleted = false )")
    Page<Question> searchQuestion(String keyword, UUID id, LocalDateTime dateStart, LocalDateTime dateEnd, UUID createdBy, UUID lessonId, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE " +
            "(q.id = :id)" + " AND (q.isDeleted = false )")
    Optional<Question> getQuestionById(UUID id);
}
