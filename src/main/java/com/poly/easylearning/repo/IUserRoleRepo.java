package com.poly.easylearning.repo;

import com.poly.easylearning.entity.User;
import com.poly.easylearning.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRoleRepo extends JpaRepository<UserRole, UUID> {
	@Modifying(clearAutomatically = true)
	@Query("""
            DELETE FROM UserRole o WHERE o.user.id = ?1
            """)
	void deleteAllByUserId(UUID userId);
}
