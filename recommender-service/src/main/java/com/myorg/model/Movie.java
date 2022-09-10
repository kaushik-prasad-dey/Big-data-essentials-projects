package com.myorg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movie {

	private String title;
	private String  also_viewed_title;
	private int count;


	public String getAlso_viewed_title() {
		return also_viewed_title;
	}

	public void setAlso_viewed_title(String also_viewed_title) {
		this.also_viewed_title = also_viewed_title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Movie(String string) {
		// TODO Auto-generated constructor stub
	}

	public Movie() {
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
