package com.example.test.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DefalutJsonObjectMapper extends ObjectMapper {
	
	public DefalutJsonObjectMapper(){
		super();
		this.setSerializationInclusion(Include.NON_NULL);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
}
