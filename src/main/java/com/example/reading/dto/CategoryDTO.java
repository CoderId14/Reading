package com.example.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO extends AbstractDTO<CategoryDTO>{

    private String code;
    private String name;
}
