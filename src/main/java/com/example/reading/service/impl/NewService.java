package com.example.reading.service.impl;

import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.NewUpdate;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.entity.TagEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.exception.UnauthorizedException;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.CategoryRepository;
import com.example.reading.repository.NewRepository;
import com.example.reading.repository.RoleRepository;
import com.example.reading.repository.TagRepository;
import com.example.reading.repository.converter.NewConverter;
import com.example.reading.service.INewService;
import com.example.reading.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.reading.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewService implements INewService {

    private final NewRepository newRepository;

    private final CategoryRepository categoryRepository;

    private final NewConverter newConverter;

    private final TagRepository tagRepository;

    private final RoleRepository roleRepository;
    
    @Override
    public NewDTO save(NewDTO newDTO, UserPrincipal currentUser) {
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
        List<String> titles = new ArrayList<>();
        newDTO.getTags().forEach(tag -> titles.add(tag.getTitle()));
        List<TagEntity> tagEntities = new ArrayList<>();
        titles.forEach(title -> tagEntities.add(tagRepository.findByTitle(title)));

        NewEntity newEntity = newConverter.toEntity(newDTO);
        newEntity.setTags(tagEntities);
        newEntity.setCategory(categoryEntity);
        newEntity = newRepository.save(newEntity);
        return newConverter.toDTO(newEntity);
    }

    @Override
    public NewDTO update(long id,NewUpdate newUpdate, UserPrincipal currentUser) {
        log.info("Update New");

        Optional<CategoryEntity> categoryEntity = Optional.ofNullable(categoryRepository.findOneByCode(newUpdate.getCategoryCode()));

        NewEntity newEntity = newRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(NEWS, ID, id));

        if(newEntity.getCreatedBy().equals(currentUser.getUsername())
        || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            newEntity.setTitle(newUpdate.getTitle());
            newEntity.setContent(newUpdate.getContent());

            if(categoryEntity.isPresent())
                newEntity.setCategory(categoryEntity.get());

            newEntity = newRepository.save(newEntity);

            return newConverter.toDTO(newEntity);
        }
        ApiResponse apiResponse = new ApiResponse(
                Boolean.FALSE,
                "You don't have permission to edit this news");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse delete(long id, UserPrincipal currentUser) {

        NewEntity newEntity = newRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NEWS, ID, id));
        if(newEntity.getCreatedBy().equals(currentUser.getUsername())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){

            newRepository.deleteById(id);

            return new ApiResponse(Boolean.TRUE, "You successfully deleted post");

        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this post");

        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public PagedResponse<NewDTO> getAllNews(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

//        CategoryEntity category = categoryRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,CREATED_DATE);

        Page<NewEntity> news =newRepository.findAll(pageable);

        List<NewEntity> contents = news.getNumberOfElements() == 0 ?
                Collections.emptyList()
                :
                news.getContent();

        List<NewDTO> result = new ArrayList<>();
        contents.forEach(temp -> result.add(newConverter.toDTO(temp)));

        return new PagedResponse<>(result,
                news.getNumber(),
                news.getSize(),
                news.getTotalElements(),
                news.getTotalPages(),
                news.isLast());
    }

    @Override
    public PagedResponse<NewDTO> getNewsByCategory(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, id));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_DATE);
        Page<NewEntity> news = newRepository.findByCategoryId(category.getId(), pageable);

        List<NewEntity> contents = news.getNumberOfElements() == 0 ?
                Collections.emptyList()
                :
                news.getContent();
        List<NewDTO> result = new ArrayList<>();
        contents.forEach(temp -> result.add(newConverter.toDTO(temp)));
        return new PagedResponse<>(result, news.getNumber(), news.getSize(), news.getTotalElements(),
                news.getTotalPages(), news.isLast());
    }

    @Override
    public PagedResponse<NewDTO> getNewsByTags(Long id, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        TagEntity tag = tagRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(TAG, ID, id));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_DATE);

        Page<NewEntity> news = newRepository.findByTagsIn(Collections.singletonList(tag), pageable);

        List<NewEntity> contents = news.getNumberOfElements() == 0 ? Collections.emptyList() : news.getContent();

        List<NewDTO> result = new ArrayList<>();
        contents.forEach(temp -> result.add(newConverter.toDTO(temp)));
        return new PagedResponse<>(result,news.getNumber(), news.getSize(), news.getTotalElements(),
                news.getTotalPages(), news.isLast());
    }


}
