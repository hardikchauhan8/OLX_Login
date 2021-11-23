package com.olx.controller;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import com.olx.security.JwtUtil;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "olx")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsService userDetailsService;

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

            return new ResponseEntity<>(
                    jwtUtil.generateToken(
                            userDetailsService.loadUserByUsername(authenticationRequest.getUsername())
                    ), HttpStatus.OK
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Logs out authenticated user from the application.")
    @PostMapping(value = "/user/logout")
    public boolean logout(@RequestHeader("Authorization") String authToken) {

        return loginService.logout(authToken);
    }

    @ApiOperation(value = "Register a user in the application.")
    @PostMapping(value = "/user")
    public User registerUser(@RequestBody User user) {
        return loginService.registerUser(user);
    }

    @ApiOperation(value = "Get information of a user from the application.")
    @GetMapping(value = "/user")
    public ResponseEntity<User> getUserInfo(@RequestHeader("Authorization") String authToken) {
        String token = authToken.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        if (!username.isEmpty()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(token, userDetails)) {
                return new ResponseEntity<>(loginService.getUserInfo(username), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "/user/validate/token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authToken) {
        try {
            String token = authToken.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);
            if (username.isEmpty()) {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(jwtUtil.validateToken(token, userDetailsService.loadUserByUsername(username)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/user/name")
    public ResponseEntity<String> getUsername(@RequestHeader("Authorization") String authToken) {
        try {
            String token = authToken.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);
            if (username.isEmpty()) {
                return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(username, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
}
