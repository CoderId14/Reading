package com.example.reading.service;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.NewUpdate;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.NewDTO;
import com.example.reading.jwt.UserPrincipal;

public interface INewService {
    NewDTO save(NewDTO newDTO, UserPrincipal currentUser);

    NewDTO update(long id,NewUpdate newUpdate, UserPrincipal currentUser);

    ApiResponse delete(long id, UserPrincipal currentUser);

    PagedResponse<NewDTO> getAllNews(int page, int size);

    PagedResponse<NewDTO> getNewsByCategory(Long id, int page, int size);

    PagedResponse<NewDTO> getNewsByTags(Long id, int page, int size);

}
