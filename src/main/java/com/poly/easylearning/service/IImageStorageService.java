package com.poly.easylearning.service;

import com.poly.easylearning.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageStorageService {
    Image upload(MultipartFile multipartFile, String folder, String nameImg);
    void delete(String publicId);

    Image findByPublicId(String publicId);
}
