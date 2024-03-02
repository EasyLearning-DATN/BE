package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.DefaultValueConstants;
import com.poly.easylearning.dto.UserDTO;
import com.poly.easylearning.payload.request.UserForgotPasswordRequest;
import com.poly.easylearning.payload.response.LessonResponse;
import com.poly.easylearning.payload.response.ListResponse;
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
import com.poly.easylearning.service.IEmailService;
import com.poly.easylearning.service.IImageStorageService;
import com.poly.easylearning.service.RoleService;
import com.poly.easylearning.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
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
    private final IImageStorageService imageStorageService;
    private final IEmailService emailService;

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
        UserInfo userInfo = UserInfo.builder() // update:: create default img
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
    public RestResponse getInfo(User user){
        checkUserIsLocked(user);
        return RestResponse.ok(ResourceBundleConstant.USR_2005, userMapper.apply(user));
    }
    public void checkUserIsLocked(User user){
        if(user.isLocked()){
            throw new ApiRequestException(ResourceBundleConstant.USR_2010);
        }
    }
    //check it
    @Override
    public RestResponse updateInfo(User oldUser, UserUpdateRQ userUpdateRQ) {
        checkUserIsLocked(oldUser);
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
    public RestResponse updateAvatar(User user, MultipartFile avatarFile) {
        if(Objects.nonNull(avatarFile)){
            if(user.getUserInfo() != null && user.getUserInfo().getAvatar() != null) {
                storageService.delete(user.getUserInfo().getAvatar().getPublicId());
            }
            Image image = storageService
                    .upload(avatarFile, UploadFolder.USER, String.valueOf(user.getId()));
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
    public RestResponse forgotPassword(UserForgotPasswordRequest request) {
        User user = userRepo.findByEmailAndLocked(request.getEmail(), false)
                .orElseThrow(() -> new ApiRequestException(ResourceBundleConstant.USR_2002));
        String password = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(password));
        User userSave = userRepo.save(user);
        emailService.send(userSave.getUserInfo().getEmail(), emailService.buildEmailForgotPassword(password),
                ResourceBundleConstant.USR_2011);
        return RestResponse.ok(ResourceBundleConstant.USR_2011, user.getId());
    }

    @Override
    public RestResponse findAllByCondition(String name, Optional<Integer> currentPage, Optional<Integer> limitPage) {

        Pageable pageable = PageRequest.of(currentPage.orElse(1), limitPage.orElse(DefaultValueConstants.LIMIT_PAGE));
        Page<User> users = userRepo.findAllByCondition(name, pageable);
        List<UserDTO> userDTOS = users.stream().map(user -> userMapper.applyForA(user)).toList();
        ListResponse<UserDTO> listResponse = ListResponse.build(users.getTotalPages(), userDTOS);
        return RestResponse.ok(ResourceBundleConstant.USR_2007, listResponse);
    }

    @Override
    public RestResponse lockAccount(User user) {
        user.setLocked(true);
        userRepo.save(user);
        return RestResponse.ok(ResourceBundleConstant.USR_2008, user.getId());
    }
    private String generateRandomPassword() {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "@#$%^&+=";

        String allChars = upperCase + lowerCase + digits + specialChars;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        // Add at least one digit
        int randomDigitIndex = random.nextInt(digits.length());
        password.append(digits.charAt(randomDigitIndex));

        // Add at least one lowercase letter
        int randomLowerCaseIndex = random.nextInt(lowerCase.length());
        password.append(lowerCase.charAt(randomLowerCaseIndex));

        // Add at least one uppercase letter
        int randomUpperCaseIndex = random.nextInt(upperCase.length());
        password.append(upperCase.charAt(randomUpperCaseIndex));

        // Add at least one special character
        int randomSpecialCharIndex = random.nextInt(specialChars.length());
        password.append(specialChars.charAt(randomSpecialCharIndex));

        // Fill the rest of the password with random characters
        for (int i = 0; i < 4; i++) {
            int randomIndex = random.nextInt(allChars.length());
            password.append(allChars.charAt(randomIndex));
        }

        // Shuffle the password characters

        return shuffleString(password.toString());
    }
    private static String shuffleString(String string) {
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = (int) (Math.random() * characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }
}
