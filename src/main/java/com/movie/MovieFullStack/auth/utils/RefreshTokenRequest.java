package com.movie.MovieFullStack.auth.utils;

import lombok.Data;

// this req body will be used when we want a new jwt token
@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
