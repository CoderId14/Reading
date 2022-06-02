package com.example.reading.dto;

import lombok.Data;

import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class AttachmentDTO{

    private String id;

    private String fileName;
    private String fileType;
    private byte[] data;

    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;

}
