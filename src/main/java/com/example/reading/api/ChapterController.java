package com.example.reading.api;

import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.service.impl.ChapterService;
import com.example.reading.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/news/{newId}/chapter")
public class ChapterController {
    private ChapterService chapterService;

    @GetMapping
    public ResponseEntity<PagedResponse<ChapterDTO>> getAllChapters(@PathVariable(name = "newId") Long newId,
                                                                 @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<ChapterDTO> allChapter = chapterService.getAllChapter(newId, page, size);

        return new ResponseEntity< >(allChapter, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getChapter(@PathVariable(name = "id") Long id

                                                        ) {
        ChapterDTO result = chapterService.getChapter(id);

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Query chapter successfully",
                result
        ));
    }



}
