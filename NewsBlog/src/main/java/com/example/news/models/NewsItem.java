package com.example.news.models;

public abstract class NewsItem {

	public abstract String getTitle();

	public abstract String getDescription();

	public abstract String getImage();

	public abstract Rubric getRubric();

	public abstract Long getDate();

}
