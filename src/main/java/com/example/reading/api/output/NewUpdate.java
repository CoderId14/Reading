package com.example.reading.api.output;

import com.example.reading.entity.TagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUpdate {

	private String title;

	private String content;

	private  String shortDescription;

	private Set<String> categories;

	private Set<String> tags;

}
