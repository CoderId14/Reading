package com.example.reading.dto;

import com.example.reading.entity.supports.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AbstractDTO {
    private long id;
    private String createdBy;
    private Date createdDate;

    private String modifiedBy;
    private Date modifiedDate;

}
