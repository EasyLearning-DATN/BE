package com.poly.easylearning.service.impl;

import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.constant.UploadFolder;
import com.poly.easylearning.entity.Image;
import com.poly.easylearning.entity.RoleApp;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.entity.UserInfo;
import com.poly.easylearning.enums.Provider;
import com.poly.easylearning.enums.RoleName;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.jwt.IJwtService;
import com.poly.easylearning.mapper.UserMapper;
import com.poly.easylearning.payload.request.UserRQ;
import com.poly.easylearning.payload.request.UserUpdateRQ;
import com.poly.easylearning.repo.IUserRepo;
import com.poly.easylearning.service.IImageStorageService;
import com.poly.easylearning.service.RoleService;
import com.poly.easylearning.service.IUserService;
import com.poly.easylearning.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class IUserServiceImpl implements IUserService {
    private final IUserRepo userRepo;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final IImageStorageService storageService;
    private final IJwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public RestResponse register(UserRQ userRQ) {
        Optional<User> checkUsername = userRepo.findByUsername(userRQ.getUsername());
        if(checkUsername.isPresent()){
            throw new ApiRequestException(ResourceBundleConstant.USR_2003);
        }
        Optional<User> checkEmail = userRepo.findByEmail(userRQ.getUsername());
        if(checkEmail.isPresent() && Provider.LOCAL.equals(userRQ.getProvider())){
            throw new ApiRequestException(ResourceBundleConstant.USR_2003);
        }
        User newUser = User.builder()
                .username(userRQ.getUsername())
                .password(passwordEncoder.encode(userRQ.getPassword()))
                .provider(userRQ.getProvider() != null ? userRQ.getProvider() : Provider.LOCAL)
                .locked(false)
                .enable(false)
                .isDeleted(false)
                .build();
        /* Check if the role exists in the database, add a new ROLE_USER if it doesn't exist.*/
        Set<RoleApp> roles = new HashSet<>();
        RoleApp roleApp = roleService.findRole(RoleName.ROLE_USER)
                .orElseGet(() -> {
                    RoleApp newRole = new RoleApp();
                    newRole.setName(RoleName.ROLE_USER);
                    return roleService.create(newRole);
                });
        roles.add(roleApp);
        newUser.setRoles(roles); // set role for new Account
        UserInfo userInfo = UserInfo.builder()
                .email(userRQ.getEmail())
                .fullName(userRQ.getFullName())
                .dayOfBirth(userRQ.getDayOfBirth())
                .user(newUser)
                .isDeleted(false)
                .build();
        newUser.setUserInfo(userInfo); // set user info for new Account
        userRepo.save(newUser);
        String token = jwtService.generateToken(newUser);
        return RestResponse.ok(ResourceBundleConstant.USR_2004, token);
    }
    @Override
    public RestResponse getInfo(String token){
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ApiRequestException(
                        ResourceBundleConstant.USR_2002
                ));
        return RestResponse.ok(ResourceBundleConstant.USR_2005, userMapper.apply(user));
    }

    //check it
    @Override
    public RestResponse updateInfo(UserUpdateRQ userUpdateRQ) {
        User oldUser = findById(userUpdateRQ.getUserID());
        oldUser.setUsername(userUpdateRQ.getUsername());
        UserInfo userInfo = oldUser.getUserInfo();
        userInfo.setFullName(userUpdateRQ.getFullName());
        userInfo.setEmail(userUpdateRQ.getEmail());
        userInfo.setDayOfBirth(userUpdateRQ.getDayOfBirth());
        oldUser.setUserInfo(userInfo);
        User userUpdated = userRepo.save(oldUser);
        return RestResponse.ok(ResourceBundleConstant.USR_2006, userMapper.apply(userUpdated));
    }
    private User findById(UUID userID){
        return userRepo.findById(userID)
                .orElseThrow(() -> new ApiRequestException(
                        ResourceBundleConstant.USR_2002
                ));
    }
    @Override
    public RestResponse updateAvatar(UUID userID, MultipartFile avatarFile) {
        User user = findById(userID);
        if(Objects.nonNull(avatarFile)){
            if(user.getUserInfo() != null && user.getUserInfo().getAvatar() != null) {
                storageService.delete(user.getUserInfo().getAvatar().getPublicId());
            }
            Image image = storageService
                    .upload(avatarFile, UploadFolder.USER, String.valueOf(userID));
            UserInfo userInfo = user.getUserInfo();
            userInfo.setAvatar(image);
            user.setUserInfo(userInfo);
            userRepo.save(user);
            return RestResponse.ok(ResourceBundleConstant.USR_2006, image.getUrl());
        }else{
            throw new ApiRequestException(ResourceBundleConstant.IMG_3001);
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new ApiRequestException(
                        ResourceBundleConstant.USR_2002
                ));
    }

    @Override
    public RestResponse lockAccount(UUID userID) {
        User user = findById(userID);
        if(user.getUserInfo() != null && user.getUserInfo().getAvatar() != null) {
            storageService.delete(user.getUserInfo().getAvatar().getPublicId());
        }
        user.setLocked(true);
        userRepo.save(user);
        return RestResponse.ok(ResourceBundleConstant.USR_2008, user.getId());
    }
}
