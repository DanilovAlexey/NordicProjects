package com.example.news.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BlogItem {
	public String blogTitle;
	public String blogBody;
	public String blogImage;
	public int userId;
	
}
