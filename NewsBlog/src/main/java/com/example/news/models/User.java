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
@ToString(exclude = "userPassword")
public class User {	
	public String userLogin;
	public String userPassword;
	public String userName;
	public String userAvatar;
	
}
