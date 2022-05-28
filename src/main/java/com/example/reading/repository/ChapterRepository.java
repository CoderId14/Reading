package com.example.reading.repository;

import com.example.reading.entity.ChapterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<ChapterEntity,Long> {

    Page<ChapterEntity> findByNewsId(Long id, Pageable pageable);

    Optional<ChapterEntity> findChapterEntityByIdAndChildId(Long id, Long childId);

    Optional<ChapterEntity> findChapterEntityByIdAndParentId(Long id, Long parentId);
}
