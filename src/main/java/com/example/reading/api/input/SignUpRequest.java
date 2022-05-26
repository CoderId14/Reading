package com.example.reading.api.input;


import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;

    private Set<String> roles;
}
