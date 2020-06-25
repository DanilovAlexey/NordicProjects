package com.example.news.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
public class NewsApiItem extends NewsItem {

	private String url;

	@JsonProperty("urlToImage")
	private String image;

	private String description;

	private String title;

	@JsonProperty("publishedAt")
	private String date;

	@JsonProperty("source")
	private NewsApiTag tag;

	@Override
	public Rubric getRubric() {
		return tag;
	}

	@Override
	public Long getDate() {
		var format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = format.parse(this.date.replace("T", " "));
		} catch (ParseException e) {
			System.out.println(e.getCause());
		}

		return date.getTime() / 1000;
	}

	@Override
	public String getImage() {
		return image;
	}

}
