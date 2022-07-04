package com.example.reading.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "news")
@Data
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@AllArgsConstructor
public class NewEntity extends BaseEntity{

    @Column
    private String title;

    @Column
    private  String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "news_category",
            joinColumns = @JoinColumn(name ="newId")
            , inverseJoinColumns = @JoinColumn(name = "categoryId")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CategoryEntity> categories;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag_new",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "new_id"))
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<TagEntity> tags = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<ChapterEntity> chapters = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private List<CommentNewsEntity> commentNews;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thumbnail", referencedColumnName = "id")
    private AttachmentEntity thumbnail;
}
