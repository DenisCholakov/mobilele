package org.softuni.mobilele.service;

import org.softuni.mobilele.model.dto.UserLoginDTO;
import org.softuni.mobilele.model.dto.UserRegistrationDto;

public interface UserService {
    void registerUser(UserRegistrationDto userRegistrationDto);

    boolean loginUser(UserLoginDTO userLoginDTO);

    void logoutUser();
}
