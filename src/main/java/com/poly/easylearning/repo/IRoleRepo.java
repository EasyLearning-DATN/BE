package com.poly.easylearning.repo;

import com.poly.easylearning.entity.RoleApp;
import com.poly.easylearning.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepo extends JpaRepository<RoleApp, Integer> {
    Optional<RoleApp> findByName(RoleName roleName);
}
