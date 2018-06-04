package com.example.test.util;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonUtils {
	
	public static DefalutJsonObjectMapper defalutJsonObjectMapper = new DefalutJsonObjectMapper();
	
	public static String convert(ObjectMapper objectMapper,Object obj) throws JsonGenerationException, JsonMappingException, IOException{
    	if(objectMapper == null){
    		return convert(obj);
    	}
		return objectMapper.writeValueAsString(obj);
	}
	
    public static <T> T convert(ObjectMapper objectMapper,String json,Class<T> cls) throws JsonParseException, JsonMappingException, IOException{
    	if(objectMapper == null){
    		return convert(json, cls);
    	}
    	return objectMapper.readValue(json, cls);
    }
    
	public static String convert(Object obj) throws JsonGenerationException, JsonMappingException, IOException{
		return defalutJsonObjectMapper.writeValueAsString(obj);
	}
	
    public static <T> T convert(String json,Class<T> cls) throws JsonParseException, JsonMappingException, IOException{
    	return defalutJsonObjectMapper.readValue(json, cls);
    }
    
    public static <T> List<T> convertJsonArray(String json,Class<T> listItemClz) throws JsonParseException, JsonMappingException, IOException{
    	List<LinkedHashMap> list = defalutJsonObjectMapper.readValue(json, List.class);
    	List<T> resList = new ArrayList<T>();
    	for(LinkedHashMap map:list){
    		resList.add(convert(convert(map),listItemClz));
    	}
    	return resList;
    }
    
    
    public static void main2(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
    	
    	
    	/** LinkedHashMap
    	 * 
    	 * 		List<CQPostBean> returnList = new ArrayList();

		List<LinkedHashMap> list = JacksonUtils.convert(json, ArrayList.class);

		for (LinkedHashMap m : list) {
			returnList.add(JacksonUtils.convert(JacksonUtils.convert(m),
					CQPostBean.class));
		}
    	 * 
    	 * */
    	
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	objectMapper.setSerializationInclusion(Include.NON_NULL);
    	
    	//objectMapper.configure(SerializationFeature., state)
    	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	
    //	System.out.println(convert(objectMapper, Result.failure("code", "msg")));
    //	Result res = convert(null, "{\"success\":false,\"code\":\"code\",\"message\":\"msg\",\"cgg\":true}", Result.class);
    	//System.out.println(res);
	}
    
}
