package com.example.reading.entity;

import com.example.reading.entity.supports.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name ="attachment")
public class AttachmentEntity{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Setter(AccessLevel.NONE)
    private String id;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    @Column
    @CreatedBy
    private String createdBy;
    @Column
    @CreatedDate
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createdDate;
    @Column
    @LastModifiedBy
    private String modifiedBy;
    @Column
    @LastModifiedDate
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date modifiedDate;

    public AttachmentEntity(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType =fileType;
        this.data = data;
    }
}
