package com.example.news.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateBlogForm {
	private String blogBody;
	private String blogImage;
	private String blogDate;
	private String userLogin;
}
