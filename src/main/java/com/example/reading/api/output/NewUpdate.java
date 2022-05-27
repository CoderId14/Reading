package com.example.reading.api.output;

import com.example.reading.entity.TagEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class NewUpdate {

	private String title;

	private String content;

	private  String shortDescription;

	private String categoryCode;

	private List<TagEntity> tags;

}
