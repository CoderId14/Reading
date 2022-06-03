package com.example.reading.api.input;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsCreate {
    private MultipartFile thumbnail;
    private String title;
    private String shortDescription;
    private String content;
    private String[] category;
    private String[] tag;
}
