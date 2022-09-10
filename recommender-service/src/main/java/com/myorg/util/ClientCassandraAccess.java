package com.myorg.util;

import java.io.IOException;

import com.myorg.model.Movie;

public interface ClientCassandraAccess {

	String retrieveAllMovies() throws Exception;

	String createMovie(Movie newMovie) throws Exception;

	String retrieveRecommendations(String title) throws IOException, Exception;

}
