package com.example.reading.repository.converter;

import com.example.reading.dto.TagDTO;
import com.example.reading.entity.TagEntity;
import org.springframework.stereotype.Component;


@Component
public class TagConverter {
    public TagEntity toEntity(TagDTO dto){
        TagEntity entity = new TagEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        return entity;
    }
    public TagDTO toDTO(TagEntity entity){
        TagDTO dto = new TagDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public TagEntity toEntity(TagDTO dto, TagEntity entity){
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        return entity;
    }
}
