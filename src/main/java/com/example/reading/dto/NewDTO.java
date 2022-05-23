package com.example.reading.dto;

import lombok.*;

@Data
public class NewDTO extends AbstractDTO<NewDTO> {
    private String title;
    private String content;
    private String shortDescription;
    private String categoryCode;
    private String thumbnail;

}
