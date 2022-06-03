package com.example.reading.repository;

import com.example.reading.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity,Long> {
    Optional<TagEntity> findByTitle(String title);
}
