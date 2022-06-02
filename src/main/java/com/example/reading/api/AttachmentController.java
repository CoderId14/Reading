package com.example.reading.api;


import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.ResponseData;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.AttachmentDTO;
import com.example.reading.service.impl.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file")MultipartFile file){
        AttachmentDTO attachmentDTO =  attachmentService.saveAttachment(file);
        String downloadURL ="";
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/attachment/download/")
                .path(attachmentDTO.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(downloadURL)).body(
                new ResponseObject(
                        "created",
                        "upload successfully",
                        new ResponseData(attachmentDTO.getFileName(),
                                downloadURL,
                                file.getContentType(),
                                file.getSize()
                        )
                )
        );
    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
        AttachmentDTO attachmentDTO = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachmentDTO.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment: filename=\"" + attachmentDTO.getFileName() +"\"")
                .body(new ByteArrayResource(attachmentDTO.getData()));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getImage(
        @PathVariable(name = "fileId") String fileId
    ){
        byte[] data = attachmentService.readFileContent(fileId);
        return ResponseEntity.ok().
        contentType(MediaType.IMAGE_JPEG).
        body(data);
    }

    @PutMapping("/{fileId}")
    public ResponseEntity<ResponseObject> updateFile(
            @PathVariable(name ="fileId") String fileId,
            @RequestParam("file") MultipartFile file
            ){

        AttachmentDTO attachmentDTO = attachmentService.updateFile(fileId,file);
        String downloadURL ="";
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/attachment/download/")
                .path(fileId)
                .toUriString();

        return ResponseEntity.created(URI.create(downloadURL)).body(
                new ResponseObject(
                        "created",
                        "file update successfully",
                        new ResponseData(attachmentDTO.getFileName(),
                                downloadURL,
                                file.getContentType(),
                                file.getSize()
                        )
                )
        );
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<ApiResponse> deleteFile(
            @PathVariable(name = "fileId") String fileId
    ){
        String deletedURL ="";
        deletedURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("api/attachment/download/")
                .path(fileId)
                .toUriString();
        ApiResponse response = attachmentService.deleteFile(fileId);
        return ResponseEntity.created(URI.create(deletedURL)).body(response);
    }
}
