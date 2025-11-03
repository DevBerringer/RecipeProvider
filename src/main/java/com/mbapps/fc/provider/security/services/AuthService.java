package com.mbapps.fc.provider.security.services;

import com.mbapps.fc.provider.security.payload.request.LoginRequest;
import com.mbapps.fc.provider.security.payload.request.RefreshTokenRequest;
import com.mbapps.fc.provider.security.payload.request.SignupRequest;
import com.mbapps.fc.provider.security.payload.response.LoginResponse;
import com.mbapps.fc.provider.security.payload.response.MessageResponse;
import com.mbapps.fc.provider.security.payload.response.TokenResponse;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;

public interface AuthService {
    MessageResponse registerUser(SignupRequest signUpRequest);
    UserInfoResponse authenticateUserAndGenerateCookie(LoginRequest loginRequest); // Keep for backward compatibility
    LoginResponse authenticateUser(LoginRequest loginRequest);
    TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}