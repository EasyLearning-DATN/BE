package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Answer;
import com.poly.easylearning.entity.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IAnswerRepo extends JpaRepository<Answer, UUID> {
    @Query("SELECT a FROM Answer a WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR a.value LIKE %:keyword%)" + " AND (a.isDeleted = false )")
    Page<Answer> searchAnswer(String keyword, Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE " +
            "(a.id = :id)" + " AND (a.isDeleted = false )")
    Optional<Answer> getAnswerById(UUID id);
}
