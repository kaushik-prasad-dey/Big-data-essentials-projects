package com.myorg.dao;

import java.io.IOException;
import java.util.ArrayList;

import com.myorg.model.Movie;
import com.myorg.model.RecommendedMovie;

public interface CassandraClient {

	public abstract void setBuilder(CassandraConnectionBuilder builder);

	public abstract ArrayList<Movie> retrieveMovies()
			throws Exception;

	public abstract Movie createMovie(Movie newMovie)
			throws Exception;

	public abstract ArrayList<RecommendedMovie> retrieveRecommendation(String title);


	/*public abstract Client createClient(Client newClient)
			throws ItemCreateException, IOException, Exception;

	public abstract Client updateClient(Client clientData) throws Exception;

	public abstract Boolean deleteClient(String clientId) throws IOException, Exception;*/


}