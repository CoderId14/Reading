package com.example.reading.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chapter")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Builder
public class ChapterEntity extends BaseEntity{
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ChapterEntity parent;

    @OneToOne
    @JoinColumn(name = "child_id", referencedColumnName = "id")
    private ChapterEntity child;

    @ManyToOne
    @JoinColumn(name = "new_id")
    private NewEntity news;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private List<CommentChapterEntity> commentChapter;


}
