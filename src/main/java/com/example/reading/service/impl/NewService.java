package com.example.reading.service.impl;

import com.example.reading.api.output.MessageResponse;
import com.example.reading.repository.converter.NewConverter;
import com.example.reading.dto.NewDTO;
import com.example.reading.entity.CategoryEntity;
import com.example.reading.entity.NewEntity;
import com.example.reading.repository.CategoryRepository;
import com.example.reading.repository.NewRepository;
import com.example.reading.service.INewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewService implements INewService {
    @Autowired
    private NewRepository newRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NewConverter newConverter;
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
    public List<NewDTO> findAll(Pageable pageable) {
        List<NewDTO> result = new ArrayList<>();
        List<NewEntity> entities = newRepository.findAll(pageable).getContent();
        for(NewEntity item:entities){
            NewDTO newDTO = newConverter.toDTO(item);
            result.add(newDTO);
        }
        return result;
    }

    @Override
    public int totalItem() {
        return (int) newRepository.count();
    }


}
