package com.olx.service;

import com.olx.dto.User;
import com.olx.entity.UserEntity;
import com.olx.repository.UserRepository;
import com.olx.utils.LoginConverterUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public boolean logout(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            userEntity.setActive(false);
            userRepository.save(userEntity);
        }
        return userEntity != null && userRepository.findById(userEntity.getId()).isPresent();
    }

    @Override
    public User registerUser(User user) {
        user.setRole("ROLE_USER");
        return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.save(LoginConverterUtil.convertDtoToEntity(modelMapper, user)));
    }

    @Override
    public User getUserInfo(String username) {
        return LoginConverterUtil.convertEntityToDto(modelMapper, userRepository.findByUsername(username));
    }

    @Override
    public boolean isUserInactive(String username) {
        return !userRepository.findByUsername(username).isActive();
    }

    @Override
    public void login(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setActive(true);
        userRepository.save(userEntity);
    }

}
