package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Lesson;
import com.poly.easylearning.entity.PackageUpgrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IPackageUpgradeRepo extends JpaRepository<PackageUpgrade, UUID> {
    @Query("SELECT p FROM PackageUpgrade p WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) AND (p.isDeleted = false )")
    Page<PackageUpgrade> searchPackageUpgrade(String keyword, Pageable pageable);

    @Query("SELECT p FROM PackageUpgrade p WHERE " +
            "(p.id = :id)" + " AND (p.isDeleted = false )")
    Optional<PackageUpgrade> getPackageUpgradeById(UUID id);

    Page<PackageUpgrade> findPackageUpgradeByCreatedBy(String username, Pageable pageable);
}
