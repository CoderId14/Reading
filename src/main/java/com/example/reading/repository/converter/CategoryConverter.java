package com.example.reading.repository.converter;

import com.example.reading.dto.CategoryDTO;
import com.example.reading.entity.CategoryEntity;
import org.springframework.stereotype.Component;


@Component
public class CategoryConverter {
    public CategoryEntity toEntity(CategoryDTO dto){
        CategoryEntity entity = new CategoryEntity();
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }
    public CategoryDTO toDTO(CategoryEntity entity){
        CategoryDTO dto = new CategoryDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public CategoryEntity toEntity(CategoryDTO dto, CategoryEntity entity){
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        return entity;
    }
}
