package com.example.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO extends AbstractDTO<RoleDTO> {

    @NotBlank(message = "Role Name " + MUST_NOT_BLANK)
    private String name;
}
