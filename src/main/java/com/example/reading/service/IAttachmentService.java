package com.example.reading.service;

import com.example.reading.dto.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IAttachmentService {

    public AttachmentDTO saveAttachment(MultipartFile file);

    public AttachmentDTO getAttachment(String fileId);
}
