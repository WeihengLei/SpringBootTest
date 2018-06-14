

/*
 * Copyright (c) 2017, HSBC. All rights reserved.
 */

package com.example.test.config;


import com.example.test.service.MessageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
*
 */
@Configuration
public abstract class WebMvcConfig implements WebMvcConfigurer {
	
	@Autowired
	protected MessageResourceService messageResourceService;

	
	@Bean(name = "messageSource")
	protected MessageSource getMessageSource() {


		this.messageResourceService.printAll();

		return (MessageSource) this.messageResourceService;

	}
	
    
}
