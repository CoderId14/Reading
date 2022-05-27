package com.example.reading.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="commentsNews")
public class CommentNewsEntity extends BaseEntity{

    private String body = "Comment";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private NewEntity news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
