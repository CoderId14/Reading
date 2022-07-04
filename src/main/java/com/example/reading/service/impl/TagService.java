package com.example.reading.service.impl;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.CategoryDTO;
import com.example.reading.dto.TagDTO;
import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.TagEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.exception.UnauthorizedException;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.TagRepository;
import com.example.reading.repository.converter.TagConverter;
import com.example.reading.service.ITagService;
import com.example.reading.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.reading.utils.AppConstants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    private final RoleRepository roleRepository;

    private final TagConverter tagConverter;

    @Override
    public PagedResponse<TagDTO> getAllTags(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC ,CREATED_DATE);

        Page<TagEntity> tagEntities = tagRepository.findAll(pageable);

        List<TagEntity> content = tagEntities.getNumberOfElements() == 0 ? Collections.emptyList() : tagEntities.getContent();

        List<TagDTO> tagDTOS = new ArrayList<>();
        content.forEach(tag -> tagDTOS.add(tagConverter.toDTO(tag)));

        return new PagedResponse<>(tagDTOS,tagEntities.getNumber(), tagEntities.getSize(),
                tagEntities.getTotalElements(), tagEntities.getTotalPages(), tagEntities.isLast());
    }

    @Override
    public TagDTO getTag(Long id) {
         TagEntity tagEntity =tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(TAG,ID, id)
        );
         return tagConverter.toDTO(tagEntity);
    }

    @Override
    public TagDTO addTag(TagDTO Tag, UserPrincipal currentUser) {
        TagEntity  tagEntity = tagConverter.toEntity(Tag);
        tagRepository.save(tagEntity);
        return tagConverter.toDTO(tagEntity);
    }

    @Override
    public TagDTO updateTag(Long id, TagDTO newTag, UserPrincipal currentUser) {
        TagEntity tagEntity = tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(TAG,ID,id));
        if(tagEntity.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            tagEntity.setTitle(newTag.getTitle());
            tagEntity.setContent(newTag.getContent());
            tagRepository.save(tagEntity);

            return tagConverter.toDTO(tagEntity);
        }
        throw new UnauthorizedException("You don't have permission to edit this tag");
    }

    @Override
    public ApiResponse deleteTag(Long id, UserPrincipal currentUser) {
        TagEntity tagEntity = tagRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(TAG,ID,id));
        if(tagEntity.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            tagRepository.delete(tagEntity);

            return new ApiResponse(Boolean.TRUE, "You successfully deleted tag", HttpStatus.CREATED);
        }
        throw new UnauthorizedException("You don't have permission to edit this tag");
    }
}
