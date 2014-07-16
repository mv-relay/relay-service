package org.landcycle.controllers;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.landcycle.api.rest.JsonResponseData;
import org.landcycle.exception.AsiaApplicationException;
import org.landcycle.exception.AsiaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base abstract Asia Controller , manage the common behavior
 * 
 * @author valerio
 * 
 */
public abstract class JsonResource {

	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	protected abstract <T> JsonResponseData<T> ariaResponse(T entity);

	/**
	 * 
	 * @param entities
	 * @return
	 */
	protected abstract <T> JsonResponseData<T> ariaResponse(List<T> entities);

	/**
	 * 
	 * @param entities
	 * @return
	 */
	protected abstract <T> JsonResponseData<T> ariaResponse(T[] entities);

	/**
	 * Handle Exceptions
	 * 
	 * @param e
	 * @return
	 */
	
	
	public <T> void setLayersIo(JsonResponseData<T> response) {
		try {
//			response.setLayersIo(LayerIO.current());
		} catch (Exception e) {
		}
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.METHOD_FAILURE)
	public @ResponseBody
	JsonResponseData<Object> ariaHandler(AsiaException e, HttpServletRequest request) {
		JsonResponseData<Object> errorResponse = new JsonResponseData<Object>();
		errorResponse.setErrorCode(e.getErrorCode());
		errorResponse.setErrorDescription(e.getMessage());
		setLayersIo(errorResponse);
		return errorResponse;
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody
	JsonResponseData<Object> aria200Handler(AsiaApplicationException e, HttpServletRequest request) {
		JsonResponseData<Object> errorResponse = new JsonResponseData<Object>();
		errorResponse.setErrorCode(e.getErrorCode());
		errorResponse.setErrorDescription(e.getMessage());
		setLayersIo(errorResponse);
		return errorResponse;
	}
}
