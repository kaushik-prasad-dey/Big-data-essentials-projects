package com.myorg.util;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.dao.CassandraClient;
import com.myorg.model.Movie;
import com.myorg.model.RecommendedMovie;

/**
 * 
 * @author
 *
 */
public class ClientCassandraAccessImpl implements ClientCassandraAccess {

	@Autowired
	CassandraClient cassandraClient;


	@Value("${myorg-paas-api.websocketURL}")
	private String websocketURL;

	public ClientCassandraAccessImpl() {
	}

	private final Logger LOGGER = LoggerFactory
			.getLogger(ClientCassandraAccessImpl.class);

	// ==========================================================================================
	// ================================== Clients
	// ========================================
	// ==========================================================================================

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.myorg.util.ClientCassandraDBAccess#retrieveAllClients()
	 */
	@Override
	public String retrieveAllMovies() throws Exception {
		if (LOGGER.isDebugEnabled()) {
			this.LOGGER
					.debug("Dispatching request to retrieveAllClients from DB...");
		}
		System.out.println("cassandraClient "+cassandraClient);
		ArrayList<Movie> clientList = cassandraClient.retrieveMovies();
		ObjectMapper jsonMapper = Format.getApplicationJsonObjectMapper();
		return jsonMapper.writeValueAsString(clientList);
	}
	
	 
	@Override
	public String createMovie(Movie newMovie) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			this.LOGGER
					.debug("Dispatching request to createClient from DB...");
		}
		Movie movie = cassandraClient.createMovie(newMovie);
		ObjectMapper jsonMapper = Format.getApplicationJsonObjectMapper();
		return jsonMapper.writeValueAsString(movie);
	}


	@Override
	public String retrieveRecommendations(String title) throws IOException, Exception {
		if (LOGGER.isDebugEnabled()) {
			this.LOGGER
					.debug("Dispatching request to retrieveAllClients from DB...");
		}
		ArrayList<RecommendedMovie> clientList = cassandraClient.retrieveRecommendation(title);
		ObjectMapper jsonMapper = Format.getApplicationJsonObjectMapper();
		return jsonMapper.writeValueAsString(clientList);
	}



}
