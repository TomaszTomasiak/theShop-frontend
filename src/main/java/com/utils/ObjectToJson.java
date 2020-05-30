package com.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectToJson {
	
	public String convertToJson(Object ob) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		String json = mapper.writeValueAsString(ob);
		
		return json;
	}
}
