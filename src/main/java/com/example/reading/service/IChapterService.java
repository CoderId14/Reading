package com.example.reading.service;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.jwt.UserPrincipal;

import java.util.List;

public interface IChapterService {

    List<ChapterDTO> getChapter(Long newId, Long id);

    PagedResponse<ChapterDTO> getAllChapter(Long id, int page, int size );

    ChapterDTO addChapter(ChapterDTO chapterRequest, UserPrincipal currentUser);

    ApiResponse deleteChapter(Long newId, Long id, UserPrincipal currentUser);

    ChapterDTO updateChapter(Long newId, Long id,ChapterDTO chapterRequest, UserPrincipal currentUser);
}
