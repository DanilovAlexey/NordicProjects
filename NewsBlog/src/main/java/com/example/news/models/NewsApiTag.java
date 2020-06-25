package com.example.news.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class NewsApiTag extends Rubric {

	private String name;

	@JsonProperty("id")
	private String path;

	@Override
	public String getSlug() {
		return path;
	}

	@Override
	public String getTitle() {
		return name;
	}
}
