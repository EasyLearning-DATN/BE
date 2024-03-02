package com.poly.easylearning.service;

import com.poly.easylearning.payload.request.*;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.entity.User;
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

    RestResponse generateTokenForgotPass(String email);

    RestResponse findAllByCondition(String name, Optional<Integer> currentPage, Optional<Integer> limitPage);

    RestResponse validTokenForgotPass(String token);

    RestResponse updatePassword(User user, PasswordUpdate passwordUpdate);

    RestResponse getUserResById(UUID id);

    RestResponse changeStatus(UUID userId, UserStatusRQ userStatusRQ);

    RestResponse deleteUser(UUID userId);

    RestResponse updateUser(UserUpdateRQ userUpdateRQ, UUID userId);
}