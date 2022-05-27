package com.example.reading.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="chapter")
public class ChapterEntity extends BaseEntity{
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ChapterEntity parent_id;

    @OneToOne
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private ChapterEntity child_id;

    @ManyToOne
    @JoinColumn(name = "new_id")
    private NewEntity news;

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<CommentChapterEntity> commentChapter;


}
