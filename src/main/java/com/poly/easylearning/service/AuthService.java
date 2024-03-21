package com.poly.easylearning.service;

import com.poly.easylearning.entity.Token;
import com.poly.easylearning.enums.TokenType;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.exception.ApiRequestException;
import com.poly.easylearning.jwt.IJwtService;
import com.poly.easylearning.payload.request.AuthRequest;
import com.poly.easylearning.payload.response.AuthResponse;
import com.poly.easylearning.repo.ITokenRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final IJwtService jwtService;
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final ITokenRepo tokenRepo;

    public RestResponse authenticate(AuthRequest authRequest) {
        try{
            User user = userService.findByUsername(authRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            String token = jwtService.generateToken(user);
            saveUserToken(user, token);
            return RestResponse.ok(ResourceBundleConstant.USR_2009, new AuthResponse(token));
        }catch (NoSuchElementException | ApiRequestException e){
            throw new ApiRequestException(ResourceBundleConstant.USR_2002);
        }catch (BadCredentialsException e){
            throw new ApiRequestException(ResourceBundleConstant.USR_2012);
        }catch (Exception e){
            throw new ApiRequestException(ResourceBundleConstant.SYS_1003);
        }
    }
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .tokenType(TokenType.BEARER)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepo.save(token);
    }
}
