package com.example.reading.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="genre")
public class GenreEntity extends BaseEntity{

    @Column
    private String title;
    @Column
    private String content;

    @ManyToMany(mappedBy = "genres")
    private List<NewEntity> news = new ArrayList<>();
}
