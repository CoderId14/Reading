package com.example.reading.dto;

import com.example.reading.entity.ChapterEntity;
import com.example.reading.entity.NewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO extends AbstractDTO<ChapterDTO>{
    private String content;

    private String description;

    private ChapterEntity childId;

    private ChapterEntity parentId;

    private NewEntity newId;


}
