package com.poly.easylearning.repo;

import com.poly.easylearning.entity.QuestionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.List;
import java.util.UUID;

public interface IQuestionTestRepo extends JpaRepository<QuestionTest, UUID> {
    @Modifying
    @Query("DELETE FROM QuestionTest qt " +
            "WHERE qt.question.id IN (:questionIds) " +
            "AND qt.test.id = :testId")
    void deleteAllByQuestionAndTest(Set<UUID> questionIds, UUID testId);
}
