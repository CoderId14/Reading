package com.example.reading.service.impl;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.CategoryDTO;
import com.example.reading.entity.CategoryEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.exception.UnauthorizedException;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.CategoryRepository;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.converter.CategoryConverter;
import com.example.reading.service.ICategoryService;
import com.example.reading.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.reading.utils.AppConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryConverter categoryConverter;

    private final RoleRepository roleRepository;

    @Override
    public PagedResponse<CategoryDTO> getAllCategories(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC ,CREATED_DATE);

        Page<CategoryEntity> categoryEntities = categoryRepository.findAll(pageable);

        List<CategoryEntity> content = categoryEntities.getNumberOfElements() == 0 ? Collections.emptyList() : categoryEntities.getContent();

        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        content.forEach(category -> categoryDTOS.add(categoryConverter.toDTO(category)));

        return new PagedResponse<>(categoryDTOS,categoryEntities.getNumber(), categoryEntities.getSize(),
        categoryEntities.getTotalElements(), categoryEntities.getTotalPages(), categoryEntities.isLast());
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY,ID,id));
        return categoryConverter.toDTO(categoryEntity);
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO category, UserPrincipal currentUser) {
        CategoryEntity categoryEntity = categoryConverter.toEntity(category);
        categoryRepository.save(categoryEntity);
        return categoryConverter.toDTO(categoryEntity);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO newCategory, UserPrincipal currentUser) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY,ID,id));
        if(categoryEntity.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            categoryEntity.setName(newCategory.getName());
            categoryRepository.save(categoryEntity);

            return categoryConverter.toDTO(categoryEntity);
        }
        throw new UnauthorizedException("You don't have permission to edit this category");
    }

    @Override
    public ApiResponse deleteCategory(Long id, UserPrincipal currentUser) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(CATEGORY,ID,id));
        if(categoryEntity.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            categoryRepository.delete(categoryEntity);

            return new ApiResponse(Boolean.TRUE, "You successfully deleted category", HttpStatus.CREATED);
        }
        throw new UnauthorizedException("You don't have permission to delete this category");
    }
}
