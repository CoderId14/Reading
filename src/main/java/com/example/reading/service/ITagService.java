package com.example.reading.service;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.TagDTO;
import com.example.reading.jwt.UserPrincipal;

public interface ITagService {

    PagedResponse<TagDTO> getAllTags(int page, int size);

    TagDTO getTag(Long id);

    TagDTO addTag(TagDTO Tag, UserPrincipal currentUser);

    TagDTO updateTag(Long id, TagDTO newTag, UserPrincipal currentUser);

    ApiResponse deleteTag(Long id, UserPrincipal currentUser);
}
