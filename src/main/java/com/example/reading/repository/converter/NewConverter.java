package com.example.reading.repository.converter;

import com.example.reading.dto.NewDTO;
import com.example.reading.dto.TagDTO;
import com.example.reading.entity.NewEntity;
import com.example.reading.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NewConverter {
    public NewEntity toEntity(NewDTO dto){
        NewEntity entity = new NewEntity();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setShortDescription(dto.getShortDescription());

        return entity;
    }
    public NewDTO toDTO(NewEntity entity){
        Set<String> tags = new HashSet<>();
        if(entity.getTags() != null){

            entity.getTags().forEach(name -> tags.add(name.getTitle()));
        }
        Set<String> categories = new HashSet<>();
        entity.getCategories().forEach(name -> categories.add(name.getName()));
        NewDTO dto = NewDTO.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .shortDescription(entity.getShortDescription())
                .thumbnail(entity.getThumbnail().getId())
                .tags(tags)
                .category(categories)
                .user(entity.getCreatedBy())
                .build();

        dto.setId(entity.getId());
        dto.setUser(entity.getCreatedBy());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }

}
