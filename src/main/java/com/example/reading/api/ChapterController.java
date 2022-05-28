package com.example.reading.api;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.entity.ChapterEntity;
import com.example.reading.entity.CommentChapterEntity;
import com.example.reading.jwt.CurrentUser;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.service.impl.ChapterService;
import com.example.reading.utils.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

        return new ResponseEntity<>(allChapter, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getChapter(
            @PathVariable(name = "newId") Long newId,
            @PathVariable(name = "id") Long id

    ) {
        List<ChapterDTO> result = chapterService.getChapter(newId, id);

        return ResponseEntity.ok().body(new ResponseObject(
                "ok",
                "Query chapter successfully",
                result
        ));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ChapterDTO> addChapter(@Validated @RequestBody ChapterDTO chapterRequest,
                                                 @CurrentUser UserPrincipal currentUser) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/news/{newId}/id").toUriString());
        ChapterDTO result = chapterService.addChapter(chapterRequest, currentUser);
        return ResponseEntity.created(uri).body(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteChapter(
            @PathVariable(name = "newId") Long newId,
            @PathVariable(name = "id") Long id,
            @CurrentUser UserPrincipal currentUser
    ) {
        ApiResponse response = chapterService.deleteChapter(newId,id,currentUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


}
