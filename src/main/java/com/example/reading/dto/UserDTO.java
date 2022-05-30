package com.example.reading.dto;

import com.example.reading.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends AbstractDTO<UserDTO>{

    @NotBlank(message = "username " + MUST_NOT_BLANK)
    private String username;

    @NotBlank(message = "password " +MUST_NOT_BLANK)
    private String password;
    private String fullName = null;
    private boolean status = true;

    @Email
    private String email;
    private Set<RoleEntity> roles;

}
