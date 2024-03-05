package com.poly.easylearning.repo;


import com.poly.easylearning.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICommentRepo extends JpaRepository<Comment, UUID> {

    @Query("""
			SELECT o FROM Comment o
			JOIN Lesson l ON o.lesson.id = l.id
			WHERE o.lesson.id = :lessonId
			AND o.enabled != FALSE
			AND o.isDeleted != TRUE
			""")
    Page<Comment> findPublicCommentByCondition(UUID lessonId, Pageable pageable);

	@Query("""
			SELECT o FROM Comment o
			JOIN Lesson l ON o.lesson.id = l.id
			WHERE o.lesson.id = :lessonId
			AND o.isDeleted != TRUE
			""")
	Page<Comment> findCommentByCondition(UUID lessonId, Pageable pageable);
}
