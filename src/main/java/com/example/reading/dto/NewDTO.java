package com.example.reading.dto;

import com.example.reading.entity.TagEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewDTO extends AbstractDTO<NewDTO> {
    private String title;
    private String content;
    private String shortDescription;
    private String categoryCode;
    private List<TagEntity> tags = new ArrayList<>();
    private String thumbnail;

}
