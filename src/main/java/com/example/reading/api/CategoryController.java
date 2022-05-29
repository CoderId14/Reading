package com.example.reading.api;


import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.CategoryDTO;
import com.example.reading.jwt.CurrentUser;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.CategoryRepository;
import com.example.reading.service.impl.CategoryService;
import com.example.reading.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PagedResponse<CategoryDTO>> getAllCategories(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<CategoryDTO> response =categoryService.getAllCategories(page,size);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject> addCategory(@Validated @RequestBody CategoryDTO category,
                                                      @CurrentUser UserPrincipal currentUser) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/categories").toUriString());
        CategoryDTO response = categoryService.addCategory(category, currentUser);
        return ResponseEntity.created(uri).body(new ResponseObject(
                "created",
                "Add category successfully",
                response
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable(name = "id") Long id) {
        CategoryDTO response = categoryService.getCategory(id);

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Get category successfully",
                response
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable(name = "id") Long id,
                                                   @Validated @RequestBody CategoryDTO category,
                                                      @CurrentUser UserPrincipal currentUser){
        CategoryDTO response = categoryService.updateCategory(id, category, currentUser);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/categories/"+id).toUriString());
        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Update category successfully",
                response
        ));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser){

        return new ResponseEntity<>(categoryService.deleteCategory(id, currentUser), HttpStatus.CREATED);
    }


}
