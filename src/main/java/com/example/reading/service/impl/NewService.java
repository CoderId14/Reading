package com.example.reading.service.impl;

import com.example.reading.api.output.MessageResponse;
import com.example.reading.api.output.PagedResponse;
import com.example.reading.entity.GenreEntity;
import com.example.reading.exception.ResourceNotFoundException;
import com.example.reading.repository.GenreRepository;
import com.example.reading.repository.converter.GenreConverter;
import com.example.reading.repository.converter.NewConverter;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.repository.CategoryRepository;
import com.example.reading.repository.NewRepository;
import com.example.reading.service.INewService;
import com.example.reading.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.reading.utils.AppConstants.*;

@Service
public class NewService implements INewService {
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NewConverter newConverter;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private GenreConverter genreConverter;
    
    @Override
    public NewDTO save(NewDTO newDTO) {
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
        NewEntity newEntity = newConverter.toEntity(newDTO);
        newEntity.setCategory(categoryEntity);
        newEntity = newRepository.save(newEntity);
        return newConverter.toDTO(newEntity);
    }

    @Override
    public NewDTO update(NewDTO newDTO) {
        NewEntity oldNewEntity = newRepository.findById(newDTO.getId()).orElse(null);
        NewEntity newEntity = newConverter.toEntity(newDTO,oldNewEntity);
        CategoryEntity categoryEntity = categoryRepository.findOneByCode(newDTO.getCategoryCode());
        newEntity.setCategory(categoryEntity);
        newEntity = newRepository.save(newEntity);
        return newConverter.toDTO(newEntity);
    }

    @Override
    public String delete(long[] ids) {
        for(long item: ids){
            newRepository.deleteById(item);
        }
        return "Items has been deleted";
    }

    @Override
    public PagedResponse<NewDTO> getAllNews(int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,CREATED_DATE);

        Page<NewEntity> news =newRepository.findAll(pageable);

        List<NewEntity> contents = news.getNumberOfElements() == 0 ?
                Collections.emptyList()
                :
                news.getContent();

        List<NewDTO> result = new ArrayList<>();
        contents.forEach(temp ->{
            result.add(newConverter.toDTO(temp));
        });

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
        contents.forEach(temp ->{
            result.add(newConverter.toDTO(temp));
        });
        return new PagedResponse<>(result, news.getNumber(), news.getSize(), news.getTotalElements(),
                news.getTotalPages(), news.isLast());
    }

//    @Override
//    public PagedResponse<NewDTO> getNewsByGenres(Long id, int page, int size) {
//        AppUtils.validatePageNumberAndSize(page, size);
//
//        GenreEntity genre = genreRepository.findById(id).
//                orElseThrow(() -> new ResourceNotFoundException(TAG, ID, id));
//
//        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_DATE);
//
//        Page<NewEntity> news = newRepository.findByGenre(Collections.singletonList(genre), pageable);
//
//        List<NewEntity> contents = news.getNumberOfElements() == 0 ? Collections.emptyList() : news.getContent();
//
//        List<NewDTO> result = new ArrayList<>();
//        contents.forEach(temp ->{
//            result.add(newConverter.toDTO(temp));
//        });
//        return new PagedResponse<>(result,news.getNumber(), news.getSize(), news.getTotalElements(),
//                news.getTotalPages(), news.isLast());
//    }
//

}
