package com.example.reading.api.input;


import lombok.Data;

@Data
public class LoginRequest {

    private String usernameOrEmail;


    private String password;

}
