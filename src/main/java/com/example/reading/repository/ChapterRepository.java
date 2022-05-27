package com.example.reading.repository;

import com.example.reading.entity.ChapterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<ChapterEntity,Long> {

    Page<ChapterEntity> findById(Long id, Pageable pageable);
}
