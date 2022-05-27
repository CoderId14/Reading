package com.example.reading.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="commentsChapter")
public class CommentChapterEntity extends BaseEntity{
    private String body = "Comment";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private ChapterEntity chapter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
