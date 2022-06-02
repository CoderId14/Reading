package com.example.reading.repository.converter;

import com.example.reading.dto.AttachmentDTO;
import com.example.reading.entity.AttachmentEntity;
import org.springframework.stereotype.Component;


@Component
public class AttachmentConverter {
    public AttachmentEntity toEntity(AttachmentDTO dto){
        AttachmentEntity entity = new AttachmentEntity();
        entity.setFileName(dto.getFileName());
        entity.setFileType(dto.getFileType());
        entity.setData(dto.getData());
        return entity;
    }
    public AttachmentDTO toDTO(AttachmentEntity entity){
        AttachmentDTO dto = new AttachmentDTO();
        if(entity.getId() != ""){
            dto.setId(entity.getId());
        }
        dto.setFileName(entity.getFileName());
        dto.setFileType(entity.getFileType());
        dto.setData(entity.getData());

        dto.setCreatedDate(entity.getCreatedDate());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setModifiedBy(entity.getModifiedBy());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public AttachmentEntity toEntity(AttachmentDTO dto, AttachmentEntity entity){
        entity.setFileName(dto.getFileName());
        entity.setFileType(dto.getFileType());
        entity.setData(dto.getData());
        return entity;
    }
}
