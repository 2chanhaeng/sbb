package com.mysite.sbb.user;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserCreatedDTO signup(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            userService.create(userCreateDTO);
            return UserCreatedDTO.right();
        } catch (Exception e) {
            return UserCreatedDTO.left(e.getMessage());
        }
    }
}
