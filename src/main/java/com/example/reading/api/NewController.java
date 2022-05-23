package com.example.reading.api;


import com.example.reading.api.output.NewOutPut;
import com.example.reading.dto.NewDTO;
import com.example.reading.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController

public class NewController {
    @Autowired
    private INewService newService;


    @GetMapping("/new")
    public NewOutPut showNew(@RequestParam("page") int page,
                             @RequestParam("limit") int limit
                             ) {
        Pageable pageable = PageRequest.of(page-1, limit);

        NewOutPut result = new NewOutPut();
        result.setPage(page);
        result.setTotalPage((int) Math.ceil((double) (newService.totalItem()) / limit));
        result.setListResult(newService.findAll(pageable));

        return result;
    }

    @PostMapping("/new")
    public NewDTO testAPI(@RequestBody NewDTO model) {

        return newService.save(model);
    }

    @PutMapping("/new/{id}")
    public NewDTO updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return newService.update(model);
    }

    @DeleteMapping("/new")
    public void deleteNew(@RequestBody long[] ids) {
        newService.delete(ids);
    }
}
