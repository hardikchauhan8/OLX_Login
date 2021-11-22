package com.olx.service;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import org.springframework.http.ResponseEntity;

public interface LoginService {

    boolean logout(String authToken);

    User registerUser(User user);

    User getUserInfo(String authToken);

    ResponseEntity<String> authenticateUser(AuthenticationRequest user);
}
