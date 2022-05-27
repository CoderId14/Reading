package com.example.reading.repository;

import com.example.reading.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity,Long> {
    TagEntity findByTitle(String title);
}
