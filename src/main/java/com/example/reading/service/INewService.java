package com.example.reading.service;

import com.example.reading.dto.NewDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewService {
    NewDTO save(NewDTO newDTO);
    NewDTO update(NewDTO newDTO);
    String delete(long[] ids);
    List<NewDTO> findAll(Pageable pageable);
    int totalItem();
}
