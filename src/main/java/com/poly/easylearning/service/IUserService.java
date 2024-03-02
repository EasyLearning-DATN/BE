package com.poly.easylearning.service;


import com.poly.easylearning.payload.request.UserForgotPasswordRequest;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.UserRQ;
import com.poly.easylearning.payload.request.UserUpdateRQ;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;


public interface IUserService {
    RestResponse register(UserRQ userRQ);

    RestResponse getInfo(User user);

    RestResponse lockAccount(User user);

    RestResponse updateInfo(User oldUser, UserUpdateRQ userUpdateRQ);

    RestResponse updateAvatar(User user, MultipartFile avatarFile);
    User findByUsername(String username);

    RestResponse forgotPassword(UserForgotPasswordRequest request);

    RestResponse findAllByCondition(String name, Optional<Integer> currentPage, Optional<Integer> limitPage);
}