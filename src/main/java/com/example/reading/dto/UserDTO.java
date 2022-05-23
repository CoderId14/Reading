package com.example.reading.dto;

import com.example.reading.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends AbstractDTO<UserDTO>{


    private String userName;

    private String passWord;
    private String fullName = "null";
    private boolean status = true;

    private String email;
    private Set<RoleEntity> roles;

}
