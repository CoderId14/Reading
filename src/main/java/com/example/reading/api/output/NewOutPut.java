package com.example.reading.api.output;

import com.example.reading.dto.NewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOutPut {
    private int page;
    private int totalPage;
    private List<NewDTO> listResult = new ArrayList<>();
}
