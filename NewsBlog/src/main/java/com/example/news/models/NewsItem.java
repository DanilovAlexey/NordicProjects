package com.example.news.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewsItem {
	private String newsTitle;
	private String newsBody;
	private String newsDate;
	private String newsLink;
	private String newsImage;
	
}
