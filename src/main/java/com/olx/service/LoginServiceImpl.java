package com.olx.service;

import com.olx.dto.BlacklistedToken;
import com.olx.dto.User;
import com.olx.repository.BlacklistedTokenRepository;
import com.olx.repository.UserRepository;
import com.olx.utils.LoginConverterUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public boolean logout(String authToken) {
        blacklistedTokenRepository.save(new BlacklistedToken(authToken));
        return true;
    }

    @Override
    public User registerUser(User user) {
        try {
            user.setRole("ROLE_USER");
            return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.save(LoginConverterUtil.convertDtoToEntity(modelMapper, user)));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getUserInfo(String username) {
        return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.findByUsername(username));
    }

    @Override
    public boolean validateLogin(String authToken) {
        BlacklistedToken blacklistedToken = blacklistedTokenRepository.findByBlacklistedJwt(authToken);
        return blacklistedToken != null;
    }
}
