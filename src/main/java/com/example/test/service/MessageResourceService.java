/*
 * Copyright (c) 2017, HSBC. All rights reserved.
 */

package com.example.test.service;

import java.util.Locale;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.stereotype.Repository;


/**
 * 
 *
 * @author A-team WeiHeng
 * @date 2017-09-28
 */
@Repository
public interface MessageResourceService extends ResourceLoaderAware {

	public void printAll();

	public void reload();

	public void setDefaultLocale(Locale locale);

	public String getTextByCodeNLocale(String code, Locale locale);
	
}
