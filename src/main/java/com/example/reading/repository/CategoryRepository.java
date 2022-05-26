package com.example.reading.repository;

import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    CategoryEntity findOneByCode(String code);

}
