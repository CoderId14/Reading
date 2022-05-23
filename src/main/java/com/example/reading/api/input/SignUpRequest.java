package com.example.reading.api.input;


import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    private String userName;
    private String passWord;
    private String email;

    private Set<String> roles;
}
