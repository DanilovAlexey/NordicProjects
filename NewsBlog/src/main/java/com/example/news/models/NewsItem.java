package com.example.news.models;


import java.sql.Timestamp;

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
	public String newsLink;
	public String newsImage;
	
}
