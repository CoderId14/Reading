package com.example.reading.api.input;


import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
public class LoginRequest {
    @NotBlank(message = "usernameOrEmail" + MUST_NOT_BLANK)
    private String usernameOrEmail;

    @NotBlank(message = "password" + MUST_NOT_BLANK)
    private String password;

}
