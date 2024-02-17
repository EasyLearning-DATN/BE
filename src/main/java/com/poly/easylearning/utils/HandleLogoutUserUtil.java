package com.poly.easylearning.utils;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.repo.ITokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleLogoutUserUtil implements LogoutHandler {

    private final ITokenRepo tokenRepo;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader(SystemConstant.AUTHORIZATION);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(SystemConstant.BEARER))
            return;
        jwt = authHeader.substring(7);
        var storedToken = tokenRepo.findByToken(jwt).orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepo.save(storedToken);
        }
    }
}
