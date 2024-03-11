package com.poly.easylearning.repo;

import com.poly.easylearning.entity.ViewResultType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IViewResultTypeRepo extends JpaRepository<ViewResultType, UUID> {
    Optional<ViewResultType> findViewResultTypeByCode(String code);
}
