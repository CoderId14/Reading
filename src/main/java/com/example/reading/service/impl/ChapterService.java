package com.example.reading.service.impl;

import com.example.reading.api.ChapterController;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.ChapterDTO;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.ChapterEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.repository.ChapterRepository;
import com.example.reading.repository.converter.ChapterConverter;
import com.example.reading.service.IChapterService;
import com.example.reading.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.reading.utils.AppConstants.*;

@RequiredArgsConstructor
@Service
public class ChapterService implements IChapterService {
    private final ChapterRepository chapterRepository;

    private final ChapterConverter chapterConverter;


    @Override
    public ChapterDTO getChapter(Long id) {
        ChapterEntity chapterEntity = chapterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CHAPTER, ID, id));
        return chapterConverter.toDTO(chapterEntity);
    }

    @Override
    public PagedResponse<ChapterDTO> getAllChapter(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_DATE);

        Page<ChapterEntity> chapters = chapterRepository.findById(id, pageable);

        List<ChapterEntity> contents = chapters.getNumberOfElements() == 0 ?
                Collections.emptyList()
                :
                chapters.getContent();
        List<ChapterDTO> result = new ArrayList<>();
        contents.forEach(temp ->{
            result.add(chapterConverter.toDTO(temp));
        });
        return new PagedResponse<>(result, chapters.getNumber(), chapters.getSize(),
                chapters.getTotalElements(), chapters.getTotalPages(), chapters.isLast());
    }
}
