package com.example.reading.service;

import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.ChapterDTO;

public interface IChapterService {

    ChapterDTO getChapter(Long id);

    PagedResponse<ChapterDTO> getAllChapter(Long id, int page, int size );
}
