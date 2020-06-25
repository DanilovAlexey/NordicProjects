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

public class LentaNews extends NewsItem {

	private LentaNewsInfo info;
	private LentaNewsLink links;
	private LentaNewsRubric rubric;
	private List<LentaNewsTag> tags;

	@JsonProperty("title_image")
	private LentaNewsImage titleImage;

	@Override
	public String getTitle() {
		return info.getTitle();
	}

	@Override
	public String getDescription() {
		return info.getRightcol();
	}

	@Override
	public String getImage() {
		if (titleImage != null) {

			return titleImage.getUrl();

		} else {
			return null;
		}

	}

	@Override
	public Long getDate() {
		return info.getModified();
	}

	@Override
	public String getUrl() {
		return links.getPublicField();
	}

}
