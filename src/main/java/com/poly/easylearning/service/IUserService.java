package com.poly.easylearning.service;


import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.UserRQ;
import com.poly.easylearning.payload.request.UserUpdateRQ;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


public interface IUserService {
    RestResponse register(UserRQ user);

    RestResponse getInfo(String username);

    RestResponse lockAccount(UUID userID);

    RestResponse updateInfo(UserUpdateRQ userUpdateRQ);

    RestResponse updateAvatar(UUID userID, MultipartFile avatarFile);
    User findByUsername(String username);
}