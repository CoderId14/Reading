package com.example.reading.service;

import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.NewDTO;
import com.example.reading.jwt.UserPrincipal;

public interface INewService {
    NewDTO save(NewDTO newDTO, UserPrincipal currentUser);

    NewDTO update(NewDTO newDTO, UserPrincipal currentUser);

    String delete(long[] ids,UserPrincipal currentUser);

    PagedResponse<NewDTO> getAllNews(int page, int size);

    PagedResponse<NewDTO> getNewsByCategory(Long id, int page, int size);

    PagedResponse<NewDTO> getNewsByTags(Long id, int page, int size);

}
