package org.landcycle.controllers;

import java.util.ArrayList;
import java.util.List;

import org.landcycle.api.rest.JsonResponseData;
import org.landcycle.api.rest.JsonResponseSingleData;
import org.landcycle.utils.CommonUtils;


/**
 * Base Asia Controller , manage the common behavior
 * 
 * @author valerio
 * 
 */
public class BaseRestController extends JsonResource {

//	protected static Logger log = LoggerFactory.getLogger(BaseRestController.class);

//	@Resource(name = "mapErrorFiledCode")
//	HashMap<String, String> mapErrorFiledCode;

	@Override
	protected <T> JsonResponseData<T> ariaResponse(T entity) {
		JsonResponseData<T> response = new JsonResponseData<T>();
		response.setAttributes(entity);
		setLayersIo(response);
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> JsonResponseData<T> ariaResponse(T[] entities) {
		JsonResponseData<T> response = new JsonResponseData<T>();
		if (entities != null && entities.length > 0) {
			List<JsonResponseSingleData<T>> protocolList = null;
			protocolList = new ArrayList<JsonResponseSingleData<T>>();
			for (T t : entities) {
				JsonResponseSingleData<T> mytmp = new JsonResponseSingleData<T>();
				mytmp.setAttributes(t);
				protocolList.add(mytmp);
//				manageActions(mytmp);
			}
			JsonResponseSingleData<T>[] reals = CommonUtils.listToArray(protocolList);
			response.setEntities(reals);
//			log.debug("Returned list of entities is {}", reals);
//			setLayersIo(response);
		} else {
			response.setEntities(EMPTY_RESULTS);
//			setLayersIo(response);
//			log.warn("Returned list of entities is null or empty");
		}
		return response;
	}

	@SuppressWarnings("rawtypes")
	private JsonResponseSingleData[] EMPTY_RESULTS = new JsonResponseSingleData[0];

	@SuppressWarnings("unchecked")
	@Override
	protected <T> JsonResponseData<T> ariaResponse(List<T> entities) {
		JsonResponseData<T> response = new JsonResponseData<T>();
		if (entities != null && entities.size() > 0) {
			List<JsonResponseSingleData<T>> protocolList = null;
			protocolList = new ArrayList<JsonResponseSingleData<T>>();
			for (T t : entities) {
				JsonResponseSingleData<T> mytmp = new JsonResponseSingleData<T>();
				mytmp.setAttributes(t);
				protocolList.add(mytmp);
//				manageActions(mytmp);
			}
			JsonResponseSingleData<T>[] reals = CommonUtils.listToArray(protocolList);
			response.setEntities(reals);
//			setLayersIo(response);
//			log.debug("Returned list of entities is {}", reals);
		} else {
			response.setEntities(EMPTY_RESULTS);
//			log.warn("Returned list of entities is null or empty");
//			setLayersIo(response);
		}
		return response;
	}
}
