package com.movie.MovieFullStack.auth.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Data: auto-generates getters, setters, toString, equals, hashCode.
//@Builder: lets you create objects easily using a clean step-by-step builder pattern.

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String name;
    private String email;
    private String username;
    private String password;
}
