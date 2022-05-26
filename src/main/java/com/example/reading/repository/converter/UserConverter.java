package com.example.reading.repository.converter;

import com.example.reading.dto.RoleDTO;
import com.example.reading.dto.UserDTO;
import com.example.reading.entity.RoleEntity;
import com.example.reading.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    public UserEntity toEntity(UserDTO dto){
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFullName(dto.getFullName());
        entity.setStatus(dto.isStatus());
        return entity;
    }
    public UserDTO toDTO(UserEntity entity){
        UserDTO dto = new UserDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setFullName(entity.getFullName());
        dto.setStatus(entity.isStatus());
        dto.setRoles(entity.getRoles());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public UserEntity toEntity(UserDTO dto, UserEntity entity){
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setFullName(dto.getFullName());
        entity.setStatus(dto.isStatus());
        return entity;
    }
}
