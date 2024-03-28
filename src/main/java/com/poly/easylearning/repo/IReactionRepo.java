package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IReactionRepo extends JpaRepository<Reaction, UUID> {

	@Query("""
			SELECT o FROM Reaction o 
			JOIN Comment c ON o.comment.id = c.id
			WHERE o.comment.id = :commentId AND o.liked = :liked 
			""")
	Optional<Reaction> findByCommentIdAndIsLikedTrue(UUID commentId, boolean liked);
}
