package com.example.reading.service.impl;

import com.example.reading.api.input.NewsCreate;
import com.example.reading.api.output.ApiResponse;
import com.example.reading.api.output.NewUpdate;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.AttachmentEntity;
import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.entity.TagEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.exception.UnauthorizedException;
import com.example.reading.jwt.UserPrincipal;
import com.example.reading.repository.*;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import static com.example.reading.utils.AppConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NewService implements INewService {

    private final NewRepository newRepository;

    private final CategoryRepository categoryRepository;

    private final NewConverter newConverter;

    private final TagRepository tagRepository;

    private final RoleRepository roleRepository;

    private final AttachmentService attachmentService;
    
    @Override
    public NewDTO save(NewsCreate request, UserPrincipal currentUser) {

        AttachmentEntity attachmentEntity = null;

        if(request.getThumbnail() != null)
            attachmentEntity = attachmentService.saveImg(request.getThumbnail());

        Set<CategoryEntity> categoryEntity = new HashSet<>();

        for (String name: request.getCategory()
             ) {
            categoryEntity.add(categoryRepository.findByName(name).orElseThrow(
                    () -> new ResourceNotFoundException(CATEGORY, "Name", name)
            ));
        }


        Set<TagEntity> tagEntities = new HashSet<>();
        for (String title: request.getTag()
        ) {
            tagEntities.add(tagRepository.findByTitle(title).orElseThrow(
                    () -> new ResourceNotFoundException(TAG, "Title", title)
            ));
        }

        NewEntity newEntity = NewEntity.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .categories(categoryEntity)
                .shortDescription(request.getShortDescription())
                .thumbnail(attachmentEntity)
                .tags(tagEntities)
                .build();
        newEntity = newRepository.save(newEntity);
        return newConverter.toDTO(newEntity);
    }

    @Override
    public NewDTO update(long id,NewUpdate request, UserPrincipal currentUser) {
        log.info("Update New");

        Set<CategoryEntity> categories = new HashSet<>();
        for (String name: request.getCategories()
        ) {
            categories.add(categoryRepository.findByName(name).orElseThrow(
                    () -> new ResourceNotFoundException(CATEGORY, "Name", name)));
        }
        Set<TagEntity> tags = new HashSet<>();
        for (String title: request.getTags()
        ) {
            tags.add(tagRepository.findByTitle(title).orElseThrow(
                    () -> new ResourceNotFoundException(TAG, "Title", title) ));
        }


        NewEntity newEntity = newRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(NEWS, ID, id));

        if(newEntity.getCreatedBy().equals(currentUser.getUsername())
        || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(roleRepository.findByName(ROLE_ADMIN).toString()))){


            newEntity.setTitle(request.getTitle());
            newEntity.setContent(request.getContent());

            if(!categories.isEmpty()) newEntity.setCategories(categories);
            if(!tags.isEmpty()) newEntity.setTags(tags);

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
        Page<NewEntity> news = newRepository.findByCategoriesIn(Collections.singleton(category), pageable);

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
