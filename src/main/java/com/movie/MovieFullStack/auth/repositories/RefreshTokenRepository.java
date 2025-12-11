package com.movie.MovieFullStack.auth.repositories;

import com.movie.MovieFullStack.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
