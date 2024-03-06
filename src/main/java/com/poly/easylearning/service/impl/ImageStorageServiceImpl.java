package com.poly.easylearning.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.repo.IImageRepo;
import com.poly.easylearning.service.IImageStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageStorageServiceImpl implements IImageStorageService {

//    private static final String UPLOAD_PRESET = "ml_default";
    private final Cloudinary cloudinary;
    private final IImageRepo imageRepo;

    public Image upload(MultipartFile multipartFile, String folder) {
        String publicId = multipartFile.getOriginalFilename() + UUID.randomUUID();

        try {
            Map<String, Object> params = ObjectUtils.asMap(
                    "folder", folder,
//                    "upload_preset", UPLOAD_PRESET,
                    "resource_type", "auto",
                    "public_id", publicId,
                    "invalidate", false
            );

            Map resource = cloudinary.uploader().upload(multipartFile.getBytes(), params);
            Image newImageResponse = new Image(
                    resource.get("public_id").toString(),
                    resource.get("url").toString()
            );
            imageRepo.save(newImageResponse);
            log.info("Upload image successfully with public id {} ", newImageResponse.getPublicId());
            return newImageResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, null);
            imageRepo.deleteById(publicId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image findByPublicId(String publicId) {
        return imageRepo.findByPublicId(publicId).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.IMG_3001));
    }


}
