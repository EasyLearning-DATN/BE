package com.poly.easylearning.repo;

import com.poly.easylearning.entity.RoleApp;
import com.poly.easylearning.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRoleRepo extends JpaRepository<RoleApp, Integer> {
    Optional<RoleApp> findByName(RoleName roleName);

	@Query("""
        SELECT r FROM RoleApp r
        JOIN UserRole ur ON r.id = ur.role.id
        JOIN User u ON ur.user.id = u.id
        WHERE u.id = :userID
		""")
	List<RoleApp> findAllByUserId(UUID userID);
}
