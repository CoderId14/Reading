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
        entity.setUserName(dto.getUserName());
        entity.setPassWord(dto.getPassWord());
        entity.setFullName(dto.getFullName());
        entity.setStatus(dto.getStatus());
        return entity;
    }
    public UserDTO toDTO(UserEntity entity){
        UserDTO dto = new UserDTO();
        if(entity.getId() != 0){
            dto.setId(entity.getId());
        }
        dto.setUserName(entity.getUserName());
        dto.setPassWord(entity.getPassWord());
        dto.setFullName(entity.getFullName());
        dto.setStatus(entity.getStatus());
        dto.setRoles(entity.getRoles());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public UserEntity toEntity(UserDTO dto, UserEntity entity){
        entity.setUserName(dto.getUserName());
        entity.setPassWord(dto.getPassWord());
        entity.setFullName(dto.getFullName());
        entity.setStatus(dto.getStatus());
        return entity;
    }
}
