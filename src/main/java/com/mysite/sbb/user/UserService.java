package com.mysite.sbb.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(UserCreateDTO userCreateDTO) {
        SiteUser user = userCreateDTO.toEntity(this.passwordEncoder);
        this.userRepository.save(user);
        return user;
    }
}