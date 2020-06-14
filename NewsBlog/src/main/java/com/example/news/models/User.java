package com.example.news.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "password")
public class User {
	public static final String DEFAULT_AVATAR = "default.jpg";

	private String email;
	private String userName;
	private String password;
	private String userAvatar;

}
