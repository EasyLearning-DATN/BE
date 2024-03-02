package com.poly.easylearning.repo;

import com.poly.easylearning.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepo extends JpaRepository<User, UUID> {
	Optional<User> findByUsername(String username);

	@Query("SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id WHERE ui.email LIKE :email")
	Optional<User> findByEmail(String email);

	@Query("""
			SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id
			WHERE ui.email LIKE :email
			AND u.locked = :locked
			""")
	Optional<User> findByEmailAndLocked(String email, boolean locked);


	@Query("""
			SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id
			WHERE LOWER(ui.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))
			""")
	Page<User> findAllByCondition(String fullName, Pageable pageable);
}
