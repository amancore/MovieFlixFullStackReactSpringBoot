package com.movie.MovieFullStack.Controllers;

import com.movie.MovieFullStack.auth.entities.RefreshToken;
import com.movie.MovieFullStack.auth.entities.User;
import com.movie.MovieFullStack.auth.services.JwtService;
import com.movie.MovieFullStack.auth.services.RefreshTokenService;
import com.movie.MovieFullStack.auth.utils.AuthResponse;
import com.movie.MovieFullStack.auth.utils.LoginRequest;
import com.movie.MovieFullStack.auth.utils.RefreshTokenRequest;
import com.movie.MovieFullStack.auth.utils.RegisterRequest;
import com.movie.MovieFullStack.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    // register , login , generation for the valid access token

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
       RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
       User user = refreshToken.getUser();
       String accessToken = jwtService.generateToken(user);
       return ResponseEntity.ok(AuthResponse.builder()
               .accessToken(accessToken)
               .refreshToken(refreshToken.getRefreshToken())
               .build());
    }
}
