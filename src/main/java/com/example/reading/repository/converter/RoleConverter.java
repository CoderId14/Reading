package com.example.reading.repository.converter;

import com.example.reading.dto.RoleDTO;
import com.example.reading.entity.RoleEntity;
import org.springframework.stereotype.Component;


@Component
public class RoleConverter {
    public RoleEntity toEntity(RoleDTO dto){
        RoleEntity entity = new RoleEntity();
        entity.setName(dto.getName());
        return entity;
    }
    public RoleDTO toDTO(RoleEntity entity){
        RoleDTO dto = new RoleDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
}
