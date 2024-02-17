package com.poly.easylearning.repo;

import com.poly.easylearning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepo extends JpaRepository<User, UUID> {
	Optional<User> findUserByUsername(String username);
}
