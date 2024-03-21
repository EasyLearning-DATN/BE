package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IReactionRepo extends JpaRepository<Reaction, UUID> {
}
