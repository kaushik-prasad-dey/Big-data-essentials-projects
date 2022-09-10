/**
 * 
 */
package com.myorg.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myorg.util.exception.AbstractException;
import com.myorg.util.exception.AbstractRuntimeException;
import com.myorg.util.exception.ItemCreateException;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author
 *
 */
public interface MovieController {

	// void commonOptions(HttpServletResponse theHttpServletResponse);

	// Clients

	ResponseEntity<String> retrieveAllMovies(
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse) throws IOException,
			ItemCreateException, Exception;
	
	
	public @ResponseBody ResponseEntity<String> retrieveAllRecommendations(
			@PathVariable("title") String title,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse) throws Exception;

/*	ResponseEntity<String> retrieveSingleClient(String clientId,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse) throws IOException, Exception;

	ResponseEntity<String> createClient(Map<String, String> requestData,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse)
			throws AbstractException, AbstractRuntimeException,
			AbstractRuntimeException, IOException, ItemCreateException, Exception;

	ResponseEntity<String> updateClient(Map<String, String> requestData,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse)
			throws JsonProcessingException, IOException, Exception;

	ResponseEntity<String> deleteClient(String clientId,
			HttpServletRequest theHttpServletRequest,
			HttpServletResponse theHttpServletResponse)
			throws JsonProcessingException, IOException, Exception;*/


}
