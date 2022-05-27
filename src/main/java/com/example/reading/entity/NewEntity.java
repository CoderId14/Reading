package com.example.reading.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "news")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class NewEntity extends BaseEntity{

    @Column
    private String title;

    @Column
    private String thumbnail;

    @Column
    private  String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.ALL)
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
