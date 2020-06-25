package com.example.news.models;

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

	private String id;

	@Override
	public String getSlug() {
		if (id != null) {
			return id;
		
		} else {
			return "all";
		}

	}

	@Override
	public String getTitle() {
		return name;
	}
}
