package com.mysite.sbb.user;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreatedDTO {
    private final boolean success;
    private final Optional<String> message;

    static UserCreatedDTO right() {
        return new UserCreatedDTO(true, Optional.empty());
    }

    static UserCreatedDTO left(String message) {
        return new UserCreatedDTO(false, Optional.of(message));
    }
}
