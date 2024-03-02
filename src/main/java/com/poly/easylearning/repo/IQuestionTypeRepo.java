package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.entity.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IQuestionTypeRepo extends JpaRepository<QuestionType, UUID> {
    @Query("SELECT q FROM QuestionType q WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR q.name LIKE %:keyword%)" + " AND (q.isDeleted = false )")
    Page<QuestionType> searchQuestionType(String keyword, Pageable pageable);

    @Query("SELECT q FROM QuestionType q WHERE " +
            "(q.id = :id)" + " AND (q.isDeleted = false )")
    Optional<QuestionType> getQuestionTypeById(UUID id);
}
