package com.example.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO extends AbstractDTO<ChapterDTO>{
    private String content;
    private String description;
}
