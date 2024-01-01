package org.softuni.mobilele.service.impl;

import org.softuni.mobilele.model.dto.UserRegistrationDto;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        userRepository.save(map(userRegistrationDto));
    }

    private UserEntity map(UserRegistrationDto userRegistrationDto) {
        return new UserEntity()
                .setActive(true)
                .setFirstName(userRegistrationDto.firstName())
                .setLastName(userRegistrationDto.lastName())
                .setEmail(userRegistrationDto.email())
                .setPassword(passwordEncoder.encode(userRegistrationDto.password()));
    }
}
