package com.example.reading.service;

import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.NewDTO;

public interface INewService {
    NewDTO save(NewDTO newDTO);
    NewDTO update(NewDTO newDTO);
    String delete(long[] ids);
    PagedResponse<NewDTO> getAllNews(int page, int size);

    PagedResponse<NewDTO> getNewsByCategory(Long id, int page, int size);

    PagedResponse<NewDTO> getNewsByTags(Long id, int page, int size);

}
