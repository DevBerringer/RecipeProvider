package com.mbapps.fc.provider.security.services;

import com.mbapps.fc.provider.security.jwt.JwtUtils;
import com.mbapps.fc.provider.security.payload.request.LoginRequest;
import com.mbapps.fc.provider.security.payload.request.RefreshTokenRequest;
import com.mbapps.fc.provider.security.payload.request.SignupRequest;
import com.mbapps.fc.provider.security.payload.response.LoginResponse;
import com.mbapps.fc.provider.security.payload.response.MessageResponse;
import com.mbapps.fc.provider.security.payload.response.TokenResponse;
import com.mbapps.fc.provider.services.recipe.domain.model.ERole;
import com.mbapps.fc.provider.services.recipe.domain.model.Role;
import com.mbapps.fc.provider.services.recipe.domain.model.User;
import com.mbapps.fc.provider.services.recipe.domain.repository.RoleRepository;
import com.mbapps.fc.provider.services.recipe.domain.repository.UserRepository;
import com.mbapps.fc.provider.services.user.domain.payload.response.UserInfoResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserInfoResponse authenticateUserAndGenerateCookie(LoginRequest loginRequest) {
        LOGGER.info("Authenticating user: {}", loginRequest.getUsername());

        Authentication authentication = authenticate(loginRequest);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LOGGER.info("Authentication successful for user: {}", loginRequest.getUsername());

        return new UserInfoResponse()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .cookie(jwtCookie);
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        LOGGER.info("Authenticating user: {}", loginRequest.getUsername());

        Authentication authentication = authenticate(loginRequest);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Generate access token and refresh token
        String accessToken = jwtUtils.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LOGGER.info("Authentication successful for user: {}", loginRequest.getUsername());

        LoginResponse response = new LoginResponse();
        response.id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles);
        
        // Set LoginResponse-specific fields
        response.accessToken(accessToken);
        response.refreshToken(refreshToken);
        response.message("Login successful");

        return response;
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        LOGGER.info("Refreshing token");

        String refreshToken = refreshTokenRequest.getRefreshToken();
        
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            LOGGER.error("Refresh token is null or empty");
            throw new IllegalArgumentException("Refresh token is required");
        }

        // Validate refresh token
        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            LOGGER.error("Invalid or expired refresh token");
            throw new RuntimeException("Invalid or expired refresh token");
        }

        // Extract username from refresh token
        String username;
        try {
            username = jwtUtils.getUserNameFromJwtToken(refreshToken);
            if (username == null || username.trim().isEmpty()) {
                LOGGER.error("Username extracted from refresh token is empty");
                throw new RuntimeException("Invalid refresh token: missing user information");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to extract username from refresh token: {}", e.getMessage());
            throw new RuntimeException("Invalid or expired refresh token", e);
        }

        // Generate new access token
        String newAccessToken = jwtUtils.generateAccessToken(username);

        // Optionally rotate refresh token (generate new one)
        String newRefreshToken = jwtUtils.generateRefreshToken(username);

        LOGGER.info("Token refresh successful for user: {}", username);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {
        LOGGER.info("Registering new user: {}", signUpRequest.getUsername());

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            String message = "Username is already taken: " + signUpRequest.getUsername();
            LOGGER.warn(message);
            return new MessageResponse("Error: " + message);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            String message = "Email is already in use: " + signUpRequest.getEmail();
            LOGGER.warn(message);
            return new MessageResponse("Error: " + message);
        }

        User user = createUserFromRequest(signUpRequest);
        userRepository.save(user);

        LOGGER.info("User registered successfully: {}", signUpRequest.getUsername());
        return new MessageResponse("User registered successfully!");
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (Exception ex) {
            LOGGER.error("Authentication failed for user: {}", loginRequest.getUsername(), ex);
            throw ex;
        }
    }

    private User createUserFromRequest(SignupRequest signUpRequest) {
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        Set<Role> roles = resolveRoles(signUpRequest.getRoles());
        user.setRoles(roles);

        return user;
    }

    private Set<Role> resolveRoles(Set<String> requestedRoles) {
        Set<Role> roles = new HashSet<>();

        if (requestedRoles == null || requestedRoles.isEmpty()) {
            roles.add(getRoleByName(ERole.ROLE_USER));
            return roles;
        }

        for (String roleName : requestedRoles) {
            try {
                ERole eRole = ERole.valueOf("ROLE_" + roleName.toUpperCase());
                roles.add(getRoleByName(eRole));
            } catch (IllegalArgumentException ex) {
                LOGGER.warn("Invalid role provided '{}', assigning default ROLE_USER", roleName);
                roles.add(getRoleByName(ERole.ROLE_USER));
            }
        }

        return roles;
    }

    private Role getRoleByName(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    String message = "Role not found in database: " + roleName;
                    LOGGER.error(message);
                    return new IllegalStateException(message);
                });
    }
}
