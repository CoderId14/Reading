package com.example.reading.api;


import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.CategoryDTO;
import com.example.reading.dto.TagDTO;
import com.example.reading.jwt.CurrentUser;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.service.impl.TagService;
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
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<PagedResponse<TagDTO>> getAllTags(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<TagDTO> response =tagService.getAllTags(page,size);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseObject> addCategory(@Validated @RequestBody TagDTO tagDTO,
                                                      @CurrentUser UserPrincipal currentUser) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/categories").toUriString());
        TagDTO response = tagService.addTag(tagDTO, currentUser);
        return ResponseEntity.created(uri).body(new ResponseObject(
                "created",
                "Add category successfully",
                response
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCategory(@PathVariable(name = "id") Long id) {
        TagDTO response = tagService.getTag(id);

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Get category successfully",
                response
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable(name = "id") Long id,
                                                         @Validated @RequestBody TagDTO category,
                                                         @CurrentUser UserPrincipal currentUser){
        TagDTO response = tagService.updateTag(id, category, currentUser);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/categories/"+id).toUriString());
        return ResponseEntity.created(uri).body(new ResponseObject(
                "ok",
                "Update category successfully",
                response
        ));

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "id") Long id,
                                                      @CurrentUser UserPrincipal currentUser){

        return new ResponseEntity<>(tagService.deleteTag(id, currentUser), HttpStatus.CREATED);
    }
}
