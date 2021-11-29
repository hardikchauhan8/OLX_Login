package com.olx.repository;

import com.olx.dto.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Integer> {

    BlacklistedToken findByBlacklistedJwt(String authToken);
}
