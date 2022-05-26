package com.example.reading.repository.converter;

import com.example.reading.dto.GenreDTO;
import com.example.reading.entity.GenreEntity;
import org.springframework.stereotype.Component;


@Component
public class GenreConverter {
    public GenreEntity toEntity(GenreDTO dto){
        GenreEntity entity = new GenreEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        return entity;
    }
    public GenreDTO toDTO(GenreEntity entity){
        GenreDTO dto = new GenreDTO();
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
    public GenreEntity toEntity(GenreDTO dto, GenreEntity entity){
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        return entity;
    }
}
