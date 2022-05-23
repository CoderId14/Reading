package com.example.reading.repository.converter;

import com.example.reading.dto.ChapterDTO;
import com.example.reading.entity.ChapterEntity;
import org.springframework.stereotype.Component;

@Component
public class ChapterConverter {
    public ChapterEntity toEntity(ChapterDTO dto){
        ChapterEntity entity = new ChapterEntity();
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());

        return entity;
    }
    public ChapterDTO toDTO(ChapterEntity entity){
        ChapterDTO dto = new ChapterDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public ChapterEntity toEntity(ChapterDTO dto, ChapterEntity entity){
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        return entity;
    }
}
