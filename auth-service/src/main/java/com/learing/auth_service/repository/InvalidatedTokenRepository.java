package com.learing.auth_service.repository;

import com.learing.auth_service.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken,Long> {
    InvalidatedToken findByToken(String token);
}
