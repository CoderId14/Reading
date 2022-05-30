package com.example.reading.dto;

import com.example.reading.entity.TagEntity;
import com.example.reading.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

import static com.example.reading.utils.AppConstants.MUST_NOT_BLANK;

@Data
public class NewDTO extends AbstractDTO<NewDTO> {
    @NotBlank(message = "New title " + MUST_NOT_BLANK)
    private String title;

    private String content;

    private String shortDescription;

    @NotBlank(message = "categoryCode " + MUST_NOT_BLANK)
    private String categoryCode;

    private List<TagEntity> tags = new ArrayList<>();

    private String thumbnail;

    private UserEntity user;

}
