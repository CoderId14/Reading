package com.example.reading.repository.converter;

import com.example.reading.dto.NewDTO;
import com.example.reading.entity.NewEntity;
import org.springframework.stereotype.Component;

@Component
public class NewConverter {
    public NewEntity toEntity(NewDTO dto){
        NewEntity entity = new NewEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setShortDescription(dto.getShortDescription());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }
    public NewDTO toDTO(NewEntity entity){
        NewDTO dto = new NewDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setShortDescription(entity.getShortDescription());
        dto.setThumbnail(entity.getThumbnail());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public NewEntity toEntity(NewDTO dto, NewEntity entity){
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setShortDescription(dto.getShortDescription());
        entity.setThumbnail(dto.getThumbnail());
        return entity;
    }
}