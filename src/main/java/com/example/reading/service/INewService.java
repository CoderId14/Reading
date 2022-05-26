package com.example.reading.service;

import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.GenreEntity;
import com.example.reading.entity.NewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewService {
    NewDTO save(NewDTO newDTO);
    NewDTO update(NewDTO newDTO);
    String delete(long[] ids);
    PagedResponse<NewDTO> getAllNews(int page, int size);

    PagedResponse<NewDTO> getNewsByCategory(Long id, int page, int size);

//    PagedResponse<NewDTO> getNewsByGenres(Long id, int page, int size);

}
