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
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        if (entity.getChild() != null)
            dto.setChildId(entity.getChild().getId());
        if (entity.getParent() != null)
            dto.setParentId(entity.getParent().getId());
        dto.setNewId(entity.getNews().getId());
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
