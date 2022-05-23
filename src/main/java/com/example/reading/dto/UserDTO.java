package com.example.reading.dto;

import com.example.reading.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends AbstractDTO<UserDTO>{


    private String userName;

    private String passWord;

    private String fullName;

    private Integer status;

    private String email;
    private List<RoleEntity> roles;
}
