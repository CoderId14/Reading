package com.example.reading.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO extends AbstractDTO{

    private String title;
    private String content;
}
