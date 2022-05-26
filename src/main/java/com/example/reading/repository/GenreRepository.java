package com.example.reading.repository;

import com.example.reading.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity,Long> {
}
