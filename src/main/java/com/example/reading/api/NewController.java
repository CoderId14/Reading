package com.example.reading.api;


import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.NewDTO;
import com.example.reading.service.INewService;
import com.example.reading.service.impl.NewService;
import com.example.reading.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/api/new")
public class NewController {
    @Autowired
    private NewService newService;


    @GetMapping
    public ResponseEntity<ResponseObject> showNew(
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

    @PostMapping()
    public ResponseEntity<ResponseObject> saveNew(@RequestBody NewDTO model) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/new").toUriString());
        return ResponseEntity.created(uri).body(new ResponseObject(
                "created",
                "Save new successfully",
                newService.save(model))) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
        model.setId(id);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/new/{id}").toUriString());
        return ResponseEntity.created(uri).body(new ResponseObject(
                "update",
                "Update new successfully id = "+id,
                newService.update(model))) ;
    }

    @DeleteMapping()
    public ResponseEntity<ResponseObject> deleteNew(@RequestBody long[] ids) {
        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Delete new successfully id = "+ids,
                newService.delete(ids))) ;

    }
}
