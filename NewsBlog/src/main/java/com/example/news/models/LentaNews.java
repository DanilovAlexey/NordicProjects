package com.example.news.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)

public class LentaNews {
	
	private String type;
	private LentaNewsInfo info;
	private LentaNewsLink links;
	private LentaNewsRubric rubric;
	private List<LentaNewsTag> tags;
	
	@JsonProperty("title_image")
	private LentaNewsImage titleImage;
}
