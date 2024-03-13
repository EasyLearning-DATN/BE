package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.response.ImageResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_FILE_UPLOAD)
public class ImageMemberController {
    private final IImageStorageService imageStorageService;

    @PostMapping("")
    public ResponseEntity<RestResponse<ImageResponse>> createImage(
            @RequestParam(name = "file") MultipartFile multipartFile,
            @RequestParam(name = "folder", defaultValue = "folder") String folder ) throws BindException {
        return ResponseEntity.ok(RestResponse.created(ResourceBundleConstant.IMG_3004, ImageResponse.fromImage(imageStorageService.upload(multipartFile, folder))));
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteImage(@RequestParam(name = "id") String publicId) {
        imageStorageService.delete(publicId);
        return ResponseEntity.noContent().build();
    }
}
