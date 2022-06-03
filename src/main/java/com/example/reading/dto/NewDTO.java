package com.example.reading.dto;

import com.example.reading.entity.TagEntity;
import com.example.reading.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewDTO extends AbstractDTO<NewDTO> {
    @NotBlank(message = "New title " + MUST_NOT_BLANK)
    private String title;

    private String content;

    private String shortDescription;

    @NotBlank(message = "categoryCode " + MUST_NOT_BLANK)
    private Set<String> category;

    private Set<String> tags = new HashSet<>();

    private String thumbnail;

    private String user;

}
