package org.softuni.mobilele.service.impl;

import org.softuni.mobilele.model.dto.UserLoginDTO;
import org.softuni.mobilele.model.dto.UserRegistrationDto;
import org.softuni.mobilele.model.entity.UserEntity;
import org.softuni.mobilele.repository.UserRepository;
import org.softuni.mobilele.service.UserService;
import org.softuni.mobilele.util.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }
    
    @Override
    public void registerUser(UserRegistrationDto userRegistrationDto) {
        userRepository.save(map(userRegistrationDto));
    }

    @Override
    public boolean loginUser(UserLoginDTO userLoginDTO) {
        var userEntity = userRepository
                .findByEmail(userLoginDTO.email())
                .orElse(null);
        boolean loginSuccess = false;

        if (userEntity != null) {
            String encodedPassword = userEntity.getPassword();
            String rawPassword = userLoginDTO.password();

            loginSuccess = passwordEncoder.matches(rawPassword, encodedPassword);

            if (loginSuccess) {
                currentUser
                    .setLogged(true)
                    .setFirstName(userEntity.getFirstName())
                    .setLastName(userEntity.getLastName());
            } else {
                currentUser.logout();
            }
        }

        return loginSuccess;
    }

    @Override
    public void logoutUser() {
        currentUser.logout();
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
