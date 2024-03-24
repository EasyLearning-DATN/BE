package com.poly.easylearning.repo;

import com.poly.easylearning.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepo extends JpaRepository<User, UUID> {
	@Query("""
			SELECT u FROM User u
			WHERE u.username = :username
			AND u.isDeleted != TRUE
			""")
	Optional<User> findByUsername(String username);

	@Query("""
			SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id
			WHERE ui.email LIKE :email
			AND u.isDeleted != TRUE
			""")
	Optional<User> findByEmail(String email);

	@Query("""
			SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id
			WHERE ui.email LIKE :email
			AND u.locked = :locked
			AND u.isDeleted != TRUE
			""")
	Optional<User> findByEmailAndLocked(String email, boolean locked);


	@Query("""
			SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id
			WHERE LOWER(ui.fullName) LIKE LOWER(CONCAT('%', :fullName, '%'))
			AND u.isDeleted != TRUE
			""")
	Page<User> findAllByCondition(String fullName, Pageable pageable);

	@Query("""
			SELECT u FROM User u JOIN UserInfo ui ON u.id = ui.user.id
			WHERE u.id = :userID
			AND u.isDeleted != TRUE
			""")
	Optional<User> findById(UUID userID);

	@Query("SELECT COUNT(u) as userAmountQuarter " +
			"FROM User u WHERE (date(u.createdDate) BETWEEN :dateStart AND :dateEnd) " +
			"AND u.isDeleted = false ")
	Map<String, Long> getUserAmount(LocalDate dateStart, LocalDate dateEnd);
}
