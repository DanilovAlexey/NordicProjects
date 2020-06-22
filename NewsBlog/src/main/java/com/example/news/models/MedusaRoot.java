package com.example.news.models;

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
public class MedusaRoot extends NewsItem {

	private String url;

	@JsonProperty("share_image")
	private String image;

	private String description;

	private String title;

	@JsonProperty("modified_at")
	private Long modified;

	private MedusaTag tag;

	@Override
	public Rubric getRubric() {
		return tag;
	}

	@Override
	public Long getDate() {
		return modified;
	}

	@Override
	public String getImage() {
		return "https://meduza.io/" + image;
	}

}
