package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.ForgotPasswordRequest;
import com.poly.easylearning.payload.request.PasswordUpdateRequest;
import com.poly.easylearning.payload.request.UserUpdateRQ;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_USER)
public class UserMemberController {
    private final IUserService userService;

    @GetMapping(SystemConstant.API_USERINFO)
    public ResponseEntity<?> getInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(userService.getInfo(user));
    }

    @PatchMapping(SystemConstant.API_USERINFO)
    public ResponseEntity<?> updateInfo(
            @AuthenticationPrincipal User oldUser,
            @RequestBody UserUpdateRQ userUpdateRQ
    ) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(userService.updateInfo(oldUser, userUpdateRQ));
    }

    @PatchMapping(SystemConstant.API_AVATAR)
    public ResponseEntity<?> updateAvatar(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "avatar") MultipartFile avatarFile
    ) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(userService.updateAvatar(user, avatarFile));
    }

    @PatchMapping(SystemConstant.API_PASSWORD)
    public ResponseEntity<?> updatePassword(
            @AuthenticationPrincipal User user,
            @RequestBody PasswordUpdateRequest passwordUpdateRequest
    ) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(userService.updatePassword(user, passwordUpdateRequest));
    }

    @PatchMapping(SystemConstant.API_FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(
            @AuthenticationPrincipal User user,
            @RequestBody ForgotPasswordRequest forgotPasswordRequest
    ) {
        userService.forgotPassword(user, forgotPasswordRequest);
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body("");
    }

    @PatchMapping(SystemConstant.LOCK_USER)
    public ResponseEntity<?> lockAccount(@AuthenticationPrincipal User user) {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(userService.lockAccount(user));
    }

    @GetMapping(SystemConstant.API_LOGOUT)
    public ResponseEntity<?> logout() {
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body("OK");
    }
}
