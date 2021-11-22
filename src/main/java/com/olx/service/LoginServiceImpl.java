package com.olx.service;

import com.olx.dto.AuthenticationRequest;
import com.olx.dto.User;
import com.olx.entity.UserEntity;
import com.olx.repository.UserRepository;
import com.olx.utils.LoginConverterUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public boolean logout(String authToken) {
        UserEntity userEntity = userRepository.findByAuthToken(authToken);
        if (userEntity != null) {
            userEntity.setAuthToken(null);
            userRepository.save(userEntity);
        }
        return userEntity != null && userRepository.findById(userEntity.getId()).isPresent();
    }

    @Override
    public User registerUser(User user) {
        return LoginConverterUtil.convertEntityToDto(modelMapper, LoginConverterUtil.convertDtoToEntity(modelMapper, user));
    }

    @Override
    public User getUserInfo(String authToken) {
        return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.findByAuthToken(authToken));
    }

    @Override
    public ResponseEntity<String> authenticateUser(AuthenticationRequest user) {
        return null;
    }
}
