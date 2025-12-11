package com.movie.MovieFullStack.auth.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @NotBlank(message = "Please enter a refresh token value!")
    @Column(nullable = false, length = 500)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
