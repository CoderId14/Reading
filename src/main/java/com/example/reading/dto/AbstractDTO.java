package com.example.reading.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AbstractDTO<T> {
    private long id;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private List<T> listResult = new ArrayList<>();
}
