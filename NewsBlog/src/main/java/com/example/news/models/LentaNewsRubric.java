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
@EqualsAndHashCode
@ToString
public class LentaNewsRubric extends Rubric {

	private String slug;
	private String title;

	@Override
	public String getSlug() {
		if (slug != null) {
			return slug;

		} else {
			return "all";
		}
	}

}
