package com.myorg.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.myorg.model.ErrorDetail;
import com.myorg.model.ErrorMessage;
import com.myorg.util.Format;
import com.myorg.util.exception.ApiException;
import com.myorg.util.exception.ConfigurationException;
import com.myorg.util.exception.ItemCreateException;
import com.myorg.util.exception.ItemDeleteException;
import com.myorg.util.exception.ItemRetrievalException;
import com.myorg.util.exception.ItemUpdateException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * APIBaseController class which handles server response.
 * 
 * @author gautam.pal
 *
 */
public class AbstractApiController {

	public static final String DEFAULT_ERROR_MESSAGE_PREFIX = "Unable to handle request";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractApiController.class);

	/**
	 * Handler all exceptions excluding those with specific exception handler
	 * declared on the controller.
	 * 
	 * @param exception
	 *            occurring exception
	 * @return Returns 500 / HttpStatus.INTERNAL_SERVER_ERROR + Appropriate
	 *         message
	 */

	@ExceptionHandler(ItemCreateException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public @ResponseBody String handleItemCreateException(
			ItemCreateException exception) {
		return responseHandler(ApiException.PRECONDITION_FAILED, exception,
				HttpStatus.PRECONDITION_FAILED.toString());
	}

	@ExceptionHandler(ConfigurationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody String handleConfigurationException(
			ConfigurationException exception) {
		return responseHandler(ApiException.CONFIGURATION_EXCEPTION, exception,
				HttpStatus.CONFLICT.toString());
	}

	@ExceptionHandler(ItemRetrievalException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public @ResponseBody String handleItemRetrievalException(
			ItemRetrievalException exception) {
		return responseHandler(ApiException.RETRIEVE_FAILED, exception,
				HttpStatus.PRECONDITION_FAILED.toString());
	}

	@ExceptionHandler(ItemUpdateException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody String handleItemUpdateException(
			ItemUpdateException exception) {
		return responseHandler(ApiException.UPDATE_FAILED, exception,
				HttpStatus.FORBIDDEN.toString());
	}

	@ExceptionHandler(ItemDeleteException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public @ResponseBody String handleItemDeleteException(
			ItemDeleteException exception) {
		return responseHandler(ApiException.DELETE_FAILED, exception,
				HttpStatus.FORBIDDEN.toString());
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public @ResponseBody String handleItemCreateException(IOException exception) {
		return responseHandlerforException(ApiException.SERVICE_UNAVAILABLE,
				exception, HttpStatus.SERVICE_UNAVAILABLE.toString());
	}

	/**
	 * Handler all exceptions excluding those with specific exception handler
	 * declared on the controller.
	 * 
	 * @param exception
	 *            occurring exception
	 * @return Returns 400 /HttpStatus.BAD_REQUEST + Appropriate message
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String handleException(
			IllegalArgumentException exception) {
		return responseHandlerforException(ApiException.BAD_REQUEST, exception,
				HttpStatus.INTERNAL_SERVER_ERROR.toString());
	}

	@ExceptionHandler(JsonProcessingException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public @ResponseBody String handleJsonProcessingException(
			JsonProcessingException exception) {
		return responseHandlerforException(ApiException.UNSUPPORTED_MEDIA_TYPE,
				exception, HttpStatus.INTERNAL_SERVER_ERROR.toString());
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
	public @ResponseBody String Exception(
			Exception exception) {
		return responseHandlerForCassandra(ApiException.REQUEST_TIMEOUT, exception,
				HttpStatus.INTERNAL_SERVER_ERROR.toString());
	}

	private String responseHandler(
			com.myorg.util.exception.PaasError badRequest, Exception cause,
			String httpStatus) {

		ErrorMessage errorMessage = new ErrorMessage();
		ErrorDetail errorDetail = new ErrorDetail();

		errorMessage.setMessage(DEFAULT_ERROR_MESSAGE_PREFIX);
		errorMessage.setCode(getErrorComponent(badRequest, "code"));
		errorMessage.setDescription(getErrorComponent(badRequest.toString(),
				"description"));
		errorMessage.setHttpStatus(httpStatus);
		if (cause != null) {
			errorDetail.setCode(getErrorComponent(cause, "code"));
			errorDetail.setDescription(getErrorComponent(cause, "description"));
			errorDetail.setMessage(getErrorComponent(cause, "message"));
			errorDetail.setCause(getErrorComponent(cause, "cause"));
			errorDetail.setUniqueId(getErrorComponent(cause, "uniqueId"));
		}

		errorMessage.setErrorDetail(errorDetail);

		ObjectMapper jsonMapper = Format.getApplicationJsonObjectMapper();
		try {
			String jsonError = jsonMapper.writeValueAsString(errorMessage);
			if (LOGGER.isErrorEnabled()) {
				this.LOGGER.error(jsonError);
			}
			return jsonError;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "UNSUPPORTED_MEDIA_TYPE";
		}
	}

	private String responseHandlerForCassandra(
			com.myorg.util.exception.PaasError badRequest,
			Exception exception, String httpStatus) {

		ErrorMessage errorMessage = new ErrorMessage();
		ErrorDetail errorDetail = new ErrorDetail();

		errorMessage.setMessage(DEFAULT_ERROR_MESSAGE_PREFIX);
		errorMessage.setCode(getErrorComponent(badRequest, "code"));
		errorMessage.setDescription(getErrorComponent(badRequest.toString(),
				"description"));
		errorMessage.setHttpStatus(httpStatus);

		if (exception != null) {
			errorDetail.setCode(exception.getCause() + "");
			errorDetail.setDescription(exception.getMessage());
		}

		errorMessage.setErrorDetail(errorDetail);

		ObjectMapper jsonMapper = Format.getApplicationJsonObjectMapper();
		try {
			String jsonError = jsonMapper.writeValueAsString(errorMessage);
			if (LOGGER.isErrorEnabled()) {
				this.LOGGER.error(jsonError);
			}
			return jsonError;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "UNSUPPORTED_MEDIA_TYPE";
		}
	}

	private String responseHandlerforException(
			com.myorg.util.exception.PaasError badRequest,
			Exception exception, String httpStatus) {

		ErrorMessage errorMessage = new ErrorMessage();

		errorMessage.setMessage(DEFAULT_ERROR_MESSAGE_PREFIX);
		errorMessage.setHttpStatus(httpStatus);

		String message = exception.getMessage();
		if (message == null) {
			errorMessage.setDescription("NullPointerException");
		} else {
			errorMessage.setDescription(message);
		}

		ObjectMapper jsonMapper = Format.getApplicationJsonObjectMapper();
		try {
			String jsonError = jsonMapper.writeValueAsString(errorMessage);
			if (LOGGER.isErrorEnabled()) {
				this.LOGGER.error(jsonError);
			}
			return jsonError;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "UNSUPPORTED_MEDIA_TYPE";
		}
	}

	private String getErrorComponent(Object message, String component) {

		return message.toString().split(component + "=")[1].split("]")[0]
				.split(",")[0];

	}

}
