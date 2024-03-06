package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IImageRepo extends JpaRepository<Image, String> {
    Optional<Image> findByPublicId(String publicId);
}
