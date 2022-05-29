package com.example.reading.service;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.CategoryDTO;
import com.example.reading.jwt.UserPrincipal;

public interface ICategoryService {
    PagedResponse<CategoryDTO> getAllCategories(int page, int size);

    CategoryDTO getCategory(Long id);

    CategoryDTO addCategory(CategoryDTO category, UserPrincipal currentUser);

    CategoryDTO updateCategory(Long id, CategoryDTO newCategory, UserPrincipal currentUser);

    ApiResponse deleteCategory(Long id, UserPrincipal currentUser);
}
