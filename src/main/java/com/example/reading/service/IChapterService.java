package com.example.reading.service;

import com.example.reading.api.output.PagedResponse;
import com.example.reading.api.output.ResponseObject;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.jwt.UserPrincipal;

import java.util.List;

public interface IChapterService {

    List<ChapterDTO> getChapter(Long id);

    PagedResponse<ChapterDTO> getAllChapter(Long id, int page, int size );

    ChapterDTO addChapter(ChapterDTO chapterRequest, UserPrincipal currentUser);
}
