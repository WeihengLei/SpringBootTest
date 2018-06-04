package com.example.test.util;

import com.example.test.exceptions.BusinessException;
import com.example.test.model.TestResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class HttpUriUtil {

    public static void GetData(String header, String sdType, RestTemplateTool restTemplateTool, String url) throws BusinessException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION,header);
        final HttpEntity httpEntity = new HttpEntity(null,httpHeaders);
        final ResponseEntity<TestResponse> responseResponseEntity =
                restTemplateTool.exchange(url+"/"+sdType, HttpMethod.GET,httpEntity,TestResponse.class);
        final TestResponse testResponse = responseResponseEntity.getBody();
        if (!testResponse.getRespCode().equals("200")){
            throw new BusinessException(testResponse.getRespCode(), testResponse.getMessage());
        }
    }
}
