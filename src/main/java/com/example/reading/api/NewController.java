package com.example.reading.api;


import com.example.reading.api.output.NewOutPut;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.NewDTO;
import com.example.reading.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController("/api")
public class NewController {
    @Autowired
    private INewService newService;


    @GetMapping("/new")
    public ResponseEntity<ResponseObject> showNew(@RequestParam("page") int page,
                                                  @RequestParam("limit") int limit
                             ) {
        Pageable pageable = PageRequest.of(page-1, limit);

        NewOutPut result = new NewOutPut();
        result.setPage(page);
        result.setTotalPage((int) Math.ceil((double) (newService.totalItem()) / limit));
        result.setListResult(newService.findAll(pageable));

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Query Book successfully",
                result));
    }

    @PostMapping("/new")
    public ResponseEntity<ResponseObject> saveNew(@RequestBody NewDTO model) {

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Save new successfully",
                newService.save(model))) ;
    }

    @PutMapping("/new/{id}")
    public ResponseEntity<ResponseObject> updateNew(@RequestBody NewDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Update new successfully id = "+id,
                newService.update(model))) ;
    }

    @DeleteMapping("/new")
    public ResponseEntity<ResponseObject> deleteNew(@RequestBody long[] ids) {
        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Delete new successfully id = "+ids,
                newService.delete(ids))) ;

    }
}
