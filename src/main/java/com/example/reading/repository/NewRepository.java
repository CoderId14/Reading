package com.example.reading.repository;

import com.example.reading.entity.GenreEntity;
import com.example.reading.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewRepository extends JpaRepository<NewEntity, Long> {

    Page<NewEntity> findByCategoryId(Long categoryId, Pageable pageable);
//
//    Page<NewEntity> findByGenre(List<GenreEntity> genres, Pageable pageable);
}
