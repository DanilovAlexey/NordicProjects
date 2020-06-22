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
public class MedusaTag extends Rubric {

	private String name;
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
