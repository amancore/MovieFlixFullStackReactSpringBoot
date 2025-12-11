package com.movie.MovieFullStack.auth.services;

import com.movie.MovieFullStack.auth.entities.RefreshToken;
import com.movie.MovieFullStack.auth.entities.User;
import com.movie.MovieFullStack.auth.repositories.RefreshTokenRepository;
import com.movie.MovieFullStack.auth.repositories.UserRepository;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Ref;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor

public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshToken createRefreshToken (String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username is not found by this mail " + username));
        RefreshToken refreshToken= user.getRefreshToken();
        if (refreshToken== null){
            long refreshTokenValidity = 5*60*60*10000;
            refreshToken = RefreshToken
                    .builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
        return refreshToken;
    }
    public RefreshToken verifyRefreshToken(String refreshToken){
        RefreshToken refToken = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh token not found"));
        if(refToken.getExpirationTime().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(refToken);
            throw new RuntimeException("Refresh token expired");
        }
        return refToken;
    }
}
