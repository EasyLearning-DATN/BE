package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IQuestionRepo extends JpaRepository<Question, UUID> {
    @Query("SELECT q FROM Question q WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR q.title LIKE %:keyword% OR q.description LIKE %:keyword%)" + " AND (q.isDeleted = false )")
    Page<Question> searchQuestion(String keyword, Pageable pageable);

    @Query("SELECT q FROM Question q WHERE " +
            "(q.id = :id)" + " AND (q.isDeleted = false )")
    Optional<Question> getQuestionById(UUID id);
}
