package com.olx.controller;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import com.olx.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "olx")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    @ApiOperation(value = "Authenticate user in the application.")
    @PostMapping(value = "/user/authenticate",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication
                    = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );

            return new ResponseEntity<>("k3h4k5ljehl5kj3w4hltk", HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Logs out authenticated user from the application.")
    @PostMapping(value = "/user/logout")
    public boolean logout(@RequestHeader("authToken") String authToken) {
        return loginService.logout(authToken);
    }

    @ApiOperation(value = "Register a user in the application.")
    @PostMapping(value = "/user")
    public User registerUser(@RequestBody User user) {
        return loginService.registerUser(user);
    }

    @ApiOperation(value = "Get information of a user from the application.")
    @GetMapping(value = "/user")
    public User getUserInfo(@RequestHeader("authToken") String authToken) {
        return loginService.getUserInfo(authToken);
    }
}
