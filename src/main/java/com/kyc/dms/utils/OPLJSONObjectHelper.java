package com.kyc.dms.utils;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OPLJSONObjectHelper implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	
	public static String getStringfromListOfObject(List<?> list) throws IOException {
		if (!OPLBkUtils.isListNullOrEmpty(list)) {
			final StringWriter sw = new StringWriter();
			new ObjectMapper().writeValue(sw, list);
			return sw.toString();
		} else {
			return "[]";
		}
	}
	
	public static List getListOfObjects(String data, String key, Class<?> clazz) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
		if (key != null) {
			JsonNode node = mapper.readTree(data);
			return mapper.readValue(node.get(key).toString(),
					mapper.getTypeFactory().constructCollectionType(List.class, clazz));
		} else {
			return mapper.readValue(data, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
		}
	}
}
