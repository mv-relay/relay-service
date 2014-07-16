package org.landcycle.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.landcycle.json.AsiaMapper;

public class CommonUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	public static String bean2string(Object o) {
		String result = "";
		try {
			result = toJsonString(o);
		} catch (Exception e) {
			// ****************************************************************************
			// DO NOTHING
			// ****************************************************************************
		}
		return result;
	}
	
	public static String toJsonString(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
		String jjson = getJsonMapper().writeValueAsString(obj);
		return jjson;
	}
	public static ObjectMapper getJsonMapper() {
		if (mapper != null)
			return mapper;
		synchronized (CommonUtils.class) {
			if (mapper != null)
				return mapper;
			mapper = new AsiaMapper();
		}
		return mapper;
	}
}
