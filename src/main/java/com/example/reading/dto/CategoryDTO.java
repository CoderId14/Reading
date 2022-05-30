package com.example.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractDTO<CategoryDTO>{
    @NotBlank(message = "Category code " + MUST_NOT_BLANK)
    private String code;
    private String name;
}
