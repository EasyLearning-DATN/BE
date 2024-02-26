package com.poly.easylearning.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.repo.IImageRepo;
import com.poly.easylearning.service.IImageStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageStorageServiceImpl implements IImageStorageService {

    private static final String UPLOAD_PRESET = "ml_default";
    private final Cloudinary cloudinary;
    private final IImageRepo imageRepo;

    /**
     * Upload images to Cloudinary. If an image with the same name already exists, it will be overwritten.
     * @param multipartFile - File data (MultipartFile type).
     * @param folder - Directory where the image will be saved on Storage (e.g., '/upload').
     * @param nameImg - Name of the image (without extension) to be saved on Storage (e.g., 'abc').
     * @return The URL of the image saved on Cloudinary, accessible from anywhere.
     * @return null - If an exception occurs while uploading the image.
     * publicId = folder + nameImg
     */
    public Image upload(MultipartFile multipartFile, String folder, String nameImg) {
        try {
            Map<String, Object> params = ObjectUtils.asMap(
                    "folder", folder,
//                    "upload_preset", UPLOAD_PRESET,
                    "resource_type", "auto",
                    "public_id", nameImg,
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

    /**
     * Delete images on Cloudinary. If an image not exists.
     * @param publicId - Is a key to manager item on Cloudinary (like @Id).
     * @return The URL of the image saved on Cloudinary, accessible from anywhere.
     * @throws  RuntimeException - If an image not exists.
     * publicId = folder + nameImg
     */
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
        return imageRepo.findByPublicId(publicId);
    }


}
