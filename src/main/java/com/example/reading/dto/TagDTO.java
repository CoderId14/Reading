package com.example.reading.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO extends AbstractDTO{
    @NotBlank(message = "title " + MUST_NOT_BLANK)
    private String title;
    private String content;
}
