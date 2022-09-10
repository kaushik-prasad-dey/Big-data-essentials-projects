package com.myorg.dao;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.myorg.model.Movie;
import com.myorg.model.RecommendedMovie;

public class CassandraClientImpl implements InitializingBean, CassandraClient {

	private final Logger LOGGER = LoggerFactory
			.getLogger(CassandraClientImpl.class);

	@Autowired
	private CassandraConnectionBuilder builder;
	 Session session;
	 
	public ArrayList<Movie> retrieveMovies() throws Exception {
		session = builder.getSession();
System.out.println("session "+session);
		StringBuilder sb = 
			      new StringBuilder("SELECT * FROM ").append("movies_keyspace.movies");
			 
			    String query = sb.toString();
			    ResultSet rs = session.execute(query);
			    
			 
			    ArrayList<Movie> movies = new ArrayList<Movie>();
			    Movie movie = new Movie();
			    for (Row rows: rs){
			        movie.setTitle(rows.getString("title"));
			        movie.setAlso_viewed_title(rows.getString("also_viewed_title"));
			        movie.setCount(rows.getInt("count"));
			        movies.add(movie);
			    } 
			 
			   
			    return movies;


	}

	
	public Movie createMovie(Movie newMovie) throws Exception {
		session = builder.getSession();

		session.execute("INSERT INTO movies_keyspace.movies"
				+ " (title, also_viewed_title, count) VALUES (?, ?, ?)",
				newMovie.getTitle(), newMovie.getAlso_viewed_title(), newMovie.getCount());
		return newMovie;
	}

	
	
	@Override
	public void setBuilder(CassandraConnectionBuilder builder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<RecommendedMovie> retrieveRecommendation(String title) {
		session = builder.getSession();

		StringBuilder sb = 
			      new StringBuilder("SELECT also_viewed_title FROM ").append(" movies_keyspace.movies ")
			      .append(" where title='"+title).append("' order by count desc");
			 
			    String query = sb.toString();
			    System.out.println("query "+query);
			    ResultSet rs = session.execute(query);
			    
			 
			    ArrayList<RecommendedMovie> movies = new ArrayList<RecommendedMovie>();
			    RecommendedMovie movie = new RecommendedMovie();
			    for (Row rows: rs){
			        movie.setRecommended_title(rows.getString("also_viewed_title"));
			        movies.add(movie);
			    } 
			 
			   
			    return movies;

	}




	
}

