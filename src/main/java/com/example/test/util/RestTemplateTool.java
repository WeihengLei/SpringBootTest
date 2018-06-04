package com.example.test.util;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateTool extends RestTemplate {

	public RestTemplateTool(ClientHttpRequestFactory requestFactory) {
		super(requestFactory);
	}
}
