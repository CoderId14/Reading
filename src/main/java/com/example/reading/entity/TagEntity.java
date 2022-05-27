package com.example.reading.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tags")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TagEntity extends BaseEntity{

    @Column
    private String title;
    @Column
    private String content;

    //thêm JsonIgnore vì This is an issue with bidirectional relationships,
    // as they hold references to each other, at deserialization, Jackson runs in an infinite loop
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private List<NewEntity> news = new ArrayList<>();
}
