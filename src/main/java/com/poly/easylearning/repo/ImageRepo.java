package com.poly.easylearning.repo;

import com.poly.easylearning.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, String> {
    Image findByPublicId(String publicId);
}
