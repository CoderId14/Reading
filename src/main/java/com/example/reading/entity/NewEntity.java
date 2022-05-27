package com.example.reading.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToMany
    @JoinTable(name = "tag_new",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "new_id"))
    private List<TagEntity> tags = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private List<ChapterEntity> chapters = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    private List<CommentNewsEntity> commentNews;
}
