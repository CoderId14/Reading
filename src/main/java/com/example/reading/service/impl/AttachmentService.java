package com.example.reading.service.impl;


import com.example.reading.api.output.ApiResponse;
import com.example.reading.dto.AttachmentDTO;
import com.example.reading.entity.AttachmentEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.repository.AttachmentRepository;
import com.example.reading.repository.converter.AttachmentConverter;
import com.example.reading.service.IAttachmentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class AttachmentService implements IAttachmentService {
    private final AttachmentRepository attachmentRepository;

    private final AttachmentConverter attachmentConverter;

    private final Path storageFolder = FileSystems.getDefault().getPath("upload");

    public AttachmentService(AttachmentRepository attachmentRepository, AttachmentConverter attachmentConverter){
        this.attachmentRepository = attachmentRepository;
        this.attachmentConverter = attachmentConverter;
        try{
            Files.createDirectories(storageFolder);
        }catch (IOException e){
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }


    public AttachmentDTO saveAttachment(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")){
                throw new RuntimeException("Filename contain invalid path sequence" + fileName);
            }
            AttachmentEntity attachmentEntity =
                    new AttachmentEntity(fileName,file.getContentType(),
                            file.getBytes());

            attachmentRepository.save(attachmentEntity);
            return attachmentConverter.toDTO(attachmentEntity);
        }
        catch (Exception e){
            throw new RuntimeException("Could not save file");
        }
    }

    public AttachmentDTO getAttachment(String fileId) {
        return attachmentConverter.toDTO(
                attachmentRepository.findById(fileId).orElseThrow(
                        () -> new ResourceNotFoundException("file attachment",fileId,fileId)
                )
        );
    }

    public boolean isImageFile(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList( new String[] {"png","jpg","jpeg","bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    public AttachmentEntity saveImg(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(isImageFile(file)){
                AttachmentEntity attachmentEntity =
                        new AttachmentEntity(fileName,file.getContentType(),
                                file.getBytes());
                return attachmentRepository.save(attachmentEntity);
            }

            throw new RuntimeException("Filename contain invalid path sequence" + fileName);

        }
        catch (Exception e){
            throw new RuntimeException("Could not save file");
        }
    }

    public byte[] readFileContent(String fileId){
        AttachmentDTO data = attachmentConverter.toDTO(
                attachmentRepository.findById(fileId).orElseThrow(
                        () -> new ResourceNotFoundException("file attachment",fileId,fileId)
                )
        );
        return data.getData();
    }

    public ApiResponse deleteFile(String fileId){
        AttachmentEntity data =
                attachmentRepository.findById(fileId).orElseThrow(
                        () -> new ResourceNotFoundException("file attachment",fileId,fileId)
                );
        attachmentRepository.delete(data);
        return new ApiResponse(true, "Delete successfully filename "+ fileId);
    }

    public AttachmentDTO updateFile(String fileId, MultipartFile file)  {
        AttachmentEntity data =
                attachmentRepository.findById(fileId).orElseThrow(
                        () -> new ResourceNotFoundException("file attachment",fileId,fileId)
                );
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            data.setFileName(fileName);
            data.setData(file.getBytes());
            data.setFileType(file.getContentType());
            attachmentRepository.save(data);
            return attachmentConverter.toDTO(data);
        }catch (IOException e){
            throw new RuntimeException("Error with file");
        }
    }
}
