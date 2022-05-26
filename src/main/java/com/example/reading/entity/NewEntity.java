package com.example.reading.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "news")
@Data
public class NewEntity extends BaseEntity{

    @Column
    private String title;
    @Column
    private String thumbnail;
    @Column
    private  String shortDescription;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToMany(mappedBy = "news")
    List<UserEntity> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "genre_new",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "new_id"))
    private List<GenreEntity> genres = new ArrayList<>();


    @OneToMany(mappedBy = "news")
    private List<ChapterEntity> chapters = new ArrayList<>();
}
