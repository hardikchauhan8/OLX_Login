package com.olx.repository;

import com.olx.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByAuthToken(String authToken);

    UserEntity findByUsername(String username);
}
