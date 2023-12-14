package com.mysite.sbb.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateDTO {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;

    public SiteUser toEntity(PasswordEncoder passwordEncoder) {
        return new SiteUser(username, passwordEncoder.encode(password), email);
    }
}
