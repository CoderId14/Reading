package com.example.reading.api.input;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
public class SignUpRequest {
    @NotBlank(message = "username " + MUST_NOT_BLANK)
    private String username;
    @NotBlank(message = "password " + MUST_NOT_BLANK)
    private String password;
    @Email
    private String email;
    private String fullName;

    private Set<String> roles;
}
