package com.example.reading.api.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
public class RoleToUserForm {
    @NotBlank(message = "username " + MUST_NOT_BLANK)
    private String userName;

    private String role;
}
