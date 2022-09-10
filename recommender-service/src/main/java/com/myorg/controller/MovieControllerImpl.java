package com.myorg.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myorg.model.Movie;
import com.myorg.util.ClientCassandraAccess;
import com.myorg.util.exception.ItemCreateException;
import com.google.gson.Gson;

@Controller
public class MovieControllerImpl extends AbstractApiController implements
		MovieController {
	@Autowired
	ClientCassandraAccess clientCassandraAccess;

	/**
	 * Retrieve all clients from Cassandra
	 * @throws Exception 
	 */
	@RequestMapping(value = "/movies", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody ResponseEntity<String> retrieveAllMovies(
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse) throws Exception {
		// Retrieve the data from the Cassandra
		String jsonResponseString = clientCassandraAccess.retrieveAllMovies();

		// Return
		return new ResponseEntity<String>(jsonResponseString, HttpStatus.OK);

	}

	
	@RequestMapping(value = "/recommendations/{title}", method = RequestMethod.GET, produces = "Application/json")
	public @ResponseBody ResponseEntity<String> retrieveAllRecommendations(
			@PathVariable("title") String title,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse) throws Exception {
		// Retrieve the data from the Cassandra
		String jsonResponseString = clientCassandraAccess.retrieveRecommendations(title);

		// Return
		return new ResponseEntity<String>(jsonResponseString, HttpStatus.OK);

	}
	
	
	@RequestMapping(value = "/movies", method = RequestMethod.POST, produces = "Application/json")
	public ResponseEntity<String> createClient(
			@RequestParam Map<String, String> requestData,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse)
			throws Exception {
		// Parse the request data
		BufferedReader reader = theHttpServletRequest.getReader();
		Gson gson = new Gson();
		Movie newMovie = gson.fromJson(reader, Movie.class);

		// Retrieve the data from the Cassandra
		String jsonResponseString = clientCassandraAccess.createMovie(newMovie);

		// Return
		return new ResponseEntity<String>(jsonResponseString, HttpStatus.OK);

	}



	
}