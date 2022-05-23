package com.example.reading.api.output;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutPut {
    private String access_token;
    private String refresh_token;
    private String type = "Bearer";
    private String username;
    private List<String> roles;
}
