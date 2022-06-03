package com.example.reading.api;


import com.example.reading.api.input.NewsCreate;
import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.NewUpdate;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.NewDTO;
import com.example.reading.jwt.CurrentUser;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.service.INewService;
import com.example.reading.service.impl.NewService;
import com.example.reading.service.impl.UserService;
import com.example.reading.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/api/news")
public class NewController {
    @Autowired
    private NewService newService;
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<ResponseObject> getAllNew(
            @RequestParam(value = "page", required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size
    ) {
        PagedResponse<NewDTO> response = newService.getAllNews(page, size);
        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Query Book successfully",
                response));
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseObject> getNewsByCategory(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @PathVariable(name = "id") Long id) {
        PagedResponse<NewDTO> response = newService.getNewsByCategory(id, page, size);

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Query Book successfully",
                response
        ));
    }
    @GetMapping("/tags/{id}")
    public ResponseEntity<ResponseObject> getNewsByTags(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
            @PathVariable(name = "id") Long id) {
        PagedResponse<NewDTO> response = newService.getNewsByTags(id, page, size);

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Query Book successfully",
                response
        ));
    }

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> addNew(
            @ModelAttribute @RequestBody NewsCreate model,
            @CurrentUser UserPrincipal currentUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/new").toUriString());
        NewDTO response = newService.save(model,currentUser);
        return ResponseEntity.created(uri).body(new ResponseObject(
                "created",
                "Save new successfully",
                response)) ;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> updateNew(
            @RequestBody NewUpdate model,
            @PathVariable("id") long id,
            @CurrentUser UserPrincipal currentUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/new/"+id).toUriString());
        return ResponseEntity.created(uri).body(new ResponseObject(
                "update",
                "Update new successfully id = "+id,
                newService.update(id,model,currentUser))) ;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteNew(
            @PathVariable(name = "id") long id,
            @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = newService.delete(id,currentUser);
        return ResponseEntity.ok().body(apiResponse);

    }
}
