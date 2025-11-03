package com.mbapps.fc.provider.security.jwt;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mbapps.fc.provider.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${family-cookbook.app.jwt-secret}")
    private String jwtSecret;

    @Value("${family-cookbook.app.jwt-expiration-ms}")
    private int jwtExpirationMs;

    @Value("${family-cookbook.app.jwt-refresh-expiration-ms:604800000}")
    private int jwtRefreshExpirationMs;

    @Value("#{environment['APP_COOKIE_SECURE'] ?: 'true'}")
    private boolean secureCookie;

    @Getter
    @Value("${family-cookbook.app.jwt-cookie-name}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * Extract JWT token from Authorization header (Bearer token)
     * @param request HTTP request
     * @return JWT token string or null if not found
     */
    public String getJwtFromAuthorizationHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7).trim();
            // Return null if token is empty after trimming
            return token.isEmpty() ? null : token;
        }
        return null;
    }

    /**
     * Get JWT token from either Authorization header or cookies (for backward compatibility)
     * @param request HTTP request
     * @return JWT token string or null if not found
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        // Try Authorization header first (Bearer token)
        String token = getJwtFromAuthorizationHeader(request);
        if (token != null) {
            return token;
        }
        // Fallback to cookies for backward compatibility
        return getJwtFromCookies(request);
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookie, "")
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        return ResponseCookie.from(jwtCookie, jwt)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .build();
    }

    public String generateTokenFromUsername(String username) {
        return generateAccessToken(username);
    }

    /**
     * Generate a short-lived access token (15-30 minutes recommended)
     * @param username Username to include in token
     * @return JWT access token
     */
    public String generateAccessToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(jwtExpirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);  // 'sub' is the standard JWT claim for subject
        claims.put("type", "access"); // Token type claim

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Generate a long-lived refresh token (7-14 days recommended)
     * @param username Username to include in token
     * @return JWT refresh token
     */
    public String generateRefreshToken(String username) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(jwtRefreshExpirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("type", "refresh"); // Token type claim

        return Jwts.builder()
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key(), Jwts.SIG.HS256)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        try {
            String subject = Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            if (subject == null || subject.trim().isEmpty()) {
                logger.error("JWT token has empty or null subject");
                throw new IllegalArgumentException("JWT token subject is empty");
            }
            return subject;
        } catch (Exception e) {
            logger.error("Failed to extract username from JWT token: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid JWT token: " + e.getMessage(), e);
        }
    }

    public boolean validateJwtToken(String authToken) {
        if (authToken == null || authToken.trim().isEmpty()) {
            return false;
        }
        try {
            Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Validate if token is a refresh token
     * @param token JWT token to validate
     * @return true if token is valid and is a refresh token
     */
    public boolean validateRefreshToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        try {
            var claims = Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            String tokenType = claims.get("type", String.class);
            return "refresh".equals(tokenType);
        } catch (Exception e) {
            logger.error("Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Validate if token is an access token
     * @param token JWT token to validate
     * @return true if token is valid and is an access token
     */
    public boolean validateAccessToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        try {
            var claims = Jwts.parser()
                    .verifyWith(key())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            String tokenType = claims.get("type", String.class);
            // If type is missing, assume it's an access token for backward compatibility
            return tokenType == null || "access".equals(tokenType);
        } catch (Exception e) {
            logger.error("Invalid access token: {}", e.getMessage());
            return false;
        }
    }

    private SecretKey key() {
        if (jwtSecret == null || jwtSecret.trim().isEmpty()) {
            logger.error("JWT secret is not configured");
            throw new IllegalStateException("JWT secret key must be configured");
        }
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        } catch (IllegalArgumentException e) {
            logger.error("JWT secret key is not valid Base64: {}", e.getMessage());
            throw new IllegalStateException("JWT secret key must be a valid Base64 string", e);
        }
    }
}
