package com.example.reading.repository;

import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.TagEntity;
import com.example.reading.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface NewRepository extends JpaRepository<NewEntity, Long> {

    Page<NewEntity> findByCategoriesIn(Set<CategoryEntity> categories, Pageable pageable);

    Page<NewEntity> findByTagsIn(
            List<TagEntity> tags,
            Pageable pageable);
}
